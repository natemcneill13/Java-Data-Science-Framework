public class DataFrame {
    private HashTable<SeriesV2<Object>> tabularData;
    private int numRows;
    private int numCols;

    public DataFrame() {
        tabularData = new HashTable<>();
        numCols = 0;
        numRows = 0;
    }
    public DataFrame(String _k, SeriesV2<Object> _series) {
        tabularData = new HashTable<>();
        tabularData.insert(_k, _series);
        numCols = 1;
        numRows = _series.getLength();
    }
    public SeriesV2<Object> colLoc(String k) throws NullPointerException {
        if (k == null) throw new NullPointerException("colLoc(String k): k is null");
        return tabularData.lookup(k);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("printing the dataframe ...\n==================\n");
        for (String k : tabularData.getKeyArray()) {
            if (k == null || k.equals("[BRIDGE]")) continue;
            sb.append("[colName:\t").append(k).append("]\n");
            sb.append(tabularData.lookup(k)).append("\n");
        }
        return sb.toString();
    }
    public int getNumRows() {
        return numRows;
    }
    public int getNumCols() {
        return numCols;
    }
    public String[] getColNames() {
        return tabularData.getValidKeys();
    }

    public void addColumn(String k, SeriesV2<Object> s) throws IllegalArgumentException {
        if (numRows != 0 && s.getLength() != numRows)
            throw new IllegalArgumentException("addColumn(String k, SeriesV2<Object> s): the length of s does not match the dataframe's # of rows");
        tabularData.insert(k, s);
        if (numRows == 0) numRows = s.getLength();
        numCols = tabularData.getSize();
    }

    public void removeColumn(String k) {
        tabularData.delete(k);
        numCols = tabularData.getSize();
        if (numCols == 0) numRows = 0;
    }
}
