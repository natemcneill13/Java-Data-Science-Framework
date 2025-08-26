public class LL<T> {
    private LLNode head;
    private LLNode tail;
    private int length;

    public LL() {
        LLNode dum1 = new LLNode();
        LLNode dum2 = new LLNode();
        this.head = dum1;
        this.tail = dum2;
        this.length = 0;
        dum1.next = dum2;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("print the series ...\n==================\n");
        LLNode curr = head;
        while (curr != null) {
            sb.append(curr.getIndex()).append("\t: ").append(curr.getData()).append("\n");
            curr = curr.next;
        }
        return sb.toString();
    }
    public int getLength() {
        return this.length;
    }
    public String[] getDataArray() {
        String[] dataArray = new String[this.length];
        LLNode curr = this.head.next;
        for(int i = 0; i < this.length; i ++) {
            dataArray[i] = String.valueOf(curr.getData());
            curr = curr.next;
        }
        return dataArray;
    }
    public String[] getIndexArray() {
        String[] indexArray = new String[this.length];
        LLNode curr = this.head;
        curr = curr.next;
        for(int i = 0; i < this.length; i ++) {
            indexArray[i] = curr.getIndex();
            curr = curr.next;
        }
        return indexArray;
    }
    public void appendNode(String _index, T _data) {
        if (_index == null || _index.isEmpty()) {
            _index = Integer.toString(length);
        }
        LLNode newNode = new LLNode(_index, _data);
        LLNode curr = head;
        while (curr.next != tail) {
            curr = curr.next;
        }
        curr.next = newNode;
        newNode.next = tail;
        length++;
    }
    public LLNode searchNode(String _index) {
        LLNode curr = this.head;
        for(int i = 0; i < this.length; i++) {
            curr = curr.next;
            if(curr.getIndex().equals(_index)) {
                return curr;
            }
        }
        return null;
    }
    public void removeNode(String _index) throws IllegalArgumentException {
        LLNode curr = head;
        while(curr.next != null) {
            if(curr.next != tail && curr.next.getIndex().equals(_index)) {
                curr.next = curr.next.next;
                length--;
                return;
            }
            curr = curr.next;
        }
        throw new IllegalArgumentException("removeNode(String _index): No node with an index " + _index + " in the list");
    }
    public void updateNode(String _index, T value) throws IllegalArgumentException {
        LLNode curr = this.head.next;
        while(curr != tail) {
            if(curr.getIndex().equals(_index)) {
                curr.setData(value);
                return;
            }
            curr = curr.next;
        }
        throw new IllegalArgumentException("updateNode(String _index, T value): No node with an index " + _index + " in the list");
    }

    public class LLNode {
        private String index;
        private T data;
        private LLNode next;

        public LLNode() {
            this.index = null;
            this.data = null;
            this.next = null;
        }
        public LLNode(String _index, T _data) {
            this.index = _index;
            this.data = _data;
            this.next = null;
        }
        public String getIndex() {
            return this.index;
        }
        public T getData() {
            return this.data;
        }
        public void setData(T d) {
            this.data = d;
        }
    }
}