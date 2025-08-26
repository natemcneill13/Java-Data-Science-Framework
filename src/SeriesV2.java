public class SeriesV2<T> implements Series<T> {

    private LL<T> seriesData;
    private BST<String, T> seriesDataBST;

    public SeriesV2(String[] _rowNames, T[] _data) throws NullPointerException, IllegalArgumentException {
        if (_data == null) {
            throw new NullPointerException("Series(String[] _index, T[] _data): _data can't be null. Terminating the program");
        }

        seriesData = new LL<>();
        seriesDataBST = new BST<>();

        try {
            if (_rowNames.length != _data.length) {
                throw new IllegalArgumentException("Series(String[] _index, T[] _data): the length of _index and _data must be the same");
            }

            for (int i = 0; i < _rowNames.length; i++) {
                if (_rowNames[i] == null || _rowNames[i].isEmpty()) {
                    throw new IllegalArgumentException("Series(String[] _index, T[] _data): _rowNames is not valid");
                }
                seriesData.appendNode(_rowNames[i], _data[i]);
                seriesDataBST.addNode(_rowNames[i], _data[i]);
            }
        } catch (NullPointerException e) {
            for (int i = 0; i < _data.length; i++) {
                String index = String.valueOf(i);
                seriesData.appendNode(index, _data[i]);
                seriesDataBST.addNode(index, _data[i]);
            }
        }
    }

    public String toString() {
        return seriesData.toString();
    }

    public int getLength() {
        return seriesData.getLength();
    }

    public String[] getRowNames() {
        return seriesData.getIndexArray();
    }

    public String[] getData() {
        return seriesData.getDataArray();
    }

    public void append(String rn, T d) {
        if (rn == null || rn.isEmpty()) {
            rn = String.valueOf(getLength());
        }
        seriesData.appendNode(rn, d);
        seriesDataBST.addNode(rn, d);
    }

    public T loc(String rn) throws NullPointerException, IllegalArgumentException {
        if (rn == null) {
            throw new NullPointerException("loc(String rn): rn can't be null");
        }
        if (rn.isEmpty()) {
            throw new IllegalArgumentException("loc(String rn): rn can't be an empty string");
        }
        BST<String, T>.BSTNode node = seriesDataBST.searchNode(rn);
        return node == null ? null : node.getData();
    }

    public T[] loc(String[] rn) throws NullPointerException, IllegalArgumentException {
        if (rn == null) {
            throw new NullPointerException("loc(String[] rn): rn[] can't be null");
        }
        if (rn.length == 0) {
            throw new IllegalArgumentException("loc(String[] rn): rn[] can't be an empty array");
        }
        T[] result = (T[]) new Object[rn.length];
        for (int i = 0; i < rn.length; i++) {
            result[i] = loc(rn[i]);
        }
        return result;
    }

    public T iloc(int ind) {
        try {
            return loc(getRowNames()[ind]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("the index " + ind + " is not valid.. returning null");
            return null;
        }
    }

    public boolean drop(String rn) throws NullPointerException, IllegalArgumentException {
        if (rn == null) {
            throw new NullPointerException("drop(String rn): rn can't be null");
        }
        if (rn.isEmpty()) {
            throw new IllegalArgumentException("drop(String rn): rn can't be an empty String");
        }
        try {
            seriesData.removeNode(rn);
            seriesDataBST.removeNode(rn);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void fillNull(T value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("fillNull(T value): value can't be null");
        }
        for (String rn : getRowNames()) {
            if (loc(rn) == null) {
                seriesData.updateNode(rn, value);
                seriesDataBST.updateNode(rn, value);
            }
        }
    }
}
