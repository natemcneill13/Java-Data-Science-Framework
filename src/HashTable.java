public class HashTable<V> {
    private static final Object BRIDGE = new String("[BRIDGE]".toCharArray());
    private int size;
    private int capacity;
    private String[] keys;
    private V[] values;

    public HashTable() {
        size = 0;
        capacity = 4;
        keys = new String[capacity];
        values = (V[]) new Object[capacity];
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("printing the hash table ...\n==================\n");
        for(int i = 0; i < capacity; i++) {
            sb.append("index:\t").append(i);
            sb.append(",\tkey:\t").append(keys[i]);
            sb.append(",\tdata:\t").append(values[i]).append("\n");
        }
        return sb.toString();
    }
    public int getSize() {
        return size;
    }
    public int getCapacity() {
        return capacity;
    }
    public String[] getKeyArray() {
        String[] out = new String[capacity];
        System.arraycopy(keys, 0, out, 0, capacity);
        return out;
    }
    public V[] getDataArray() {
        V[] out = (V[]) new Object[capacity];
        System.arraycopy(values, 0, out, 0, capacity);
        return out;
    }
    public String[] getValidKeys() {
        int count = 0;
        for (int i = 0; i < capacity; i++) {
            if (ouvert(i)) count++;
        }
        String[] out = new String[count];
        for (int i = 0, j = 0; i < capacity; i++) {
            if (ouvert(i)) out[j++] = keys[i];
        }
        return out;
    }
    public int getHashIndex(String k) {
        int hashValue = 0;
        for (int i = 0; i < k.length(); i++) {
            int letter = k.charAt(i) - 96;
            hashValue += (hashValue * 27 + letter);
        }
        return hashValue % this.getCapacity();
    }
    public V lookup(String k) throws NullPointerException {
        if (k == null) throw new NullPointerException("lookup(String key): key is null");
        int start = getHashIndex(k);
        int idx   = start;

        while (keys[idx] != null) {
            if (ouvert(idx) && keys[idx].equals(k))
                return values[idx];
            idx = (idx + 1) % capacity;
            if (idx == start) break;
        }
        return null;
    }
    public int insert(String k, V v) throws NullPointerException {
        if (k == null)
            throw new NullPointerException("insert(String k, V v): k is null");
        if (v == null)
            throw new NullPointerException("insert(String k, V v): v is null");

        int idx = getHashIndex(k);
        int firstBridge = -1;

        while (keys[idx] != null) {
            if (keys[idx] == BRIDGE && firstBridge == -1)
                firstBridge = idx;
            else if (ouvert(idx) && keys[idx].equals(k)) {
                values[idx] = v;
                return idx;
            }
            idx = (idx + 1) % capacity;
        }

        int target;
        if (firstBridge != -1) target = firstBridge; else target = idx;
        keys[target]   = k;
        values[target] = v;
        size++;

        if ((double) size / capacity >= 0.55) {
            sizeUp();
            target = probeFor(k);
        }
        return target;
    }
    private void sizeUp() {
        rehash(capacity * 2);
    }
    private void sizeDown() {
        int newCap = Math.max(4, capacity / 2);
        if (newCap < capacity) rehash(newCap);
    }
    public int delete(String k) {
        if (k == null)
            throw new NullPointerException("delete(String k): k is null");

        int start = getHashIndex(k);
        int idx   = start;
        while (keys[idx] != null) {
            if (ouvert(idx) && keys[idx].equals(k)) {
                keys[idx]   = (String) BRIDGE;
                values[idx] = null;
                size--;

                if ((double) size / capacity <= 0.30)
                    sizeDown();
                return idx;
            }
            idx = (idx + 1) % capacity;
            if (idx == start) break;
        }
        return start;
    }

    // Helping little helpers

    private boolean ouvert(int i) {
        return keys[i] != null && keys[i] != BRIDGE;
    }
    private int probeFor(String k) throws IllegalStateException{
        int idx = getHashIndex(k);
        while (keys[idx] != null) {
            if (keys[idx] != BRIDGE && keys[idx].equals(k)) return idx;
            idx = (idx + 1) % capacity;
        }
        throw new IllegalStateException("probeFor(String k): key not found after rehash");
    }
    private void rehash(int newCap) {
        String[] oldKeys = keys;
        V[] oldVal = values;
        int oldCap = capacity;

        capacity = newCap;
        keys = new String[newCap];
        values = (V[]) new Object[newCap];
        size = 0;

        for (int i = 0; i < oldCap; i++) {
            if (oldKeys[i] != null && oldKeys[i] != BRIDGE)
                insert(oldKeys[i], oldVal[i]);
        }
    }
}
