public class BST<I extends Comparable<I>, T>{

    class BSTNode {
        private I index;
        private T data;
        private BSTNode left;
        private BSTNode right;

        /**
         * Default constructor. Sets all instance variables to be null.
         */
        public BSTNode() {
            index = null;
            data = null;
            left = null;
            right = null;
        }

        /**
         * Constructor. Sets data and index to be _data and _index respectively.
         */
        public BSTNode(I _index, T _data) {
            index = _index;
            data = _data;
            left = null;
            right = null;
        }

        /**
         * Returns the index stored in this node.
         */
        public I getIndex() {
            return this.index;
        }

        /**
         * Returns the data stored in this node.
         */
        public T getData() {
            return this.data;
        }

        /**
         * Updates the data in this node to the specified value.
         */
        public void setData(T d) {
            this.data = d;
        }

        /**
         * Returns a string representation of the node, indicating its index and data.
         */
        public String toString() {
            return "index:\t" + this.index + ",\tdata:\t" + this.data + "\n";
        }
    }


    private BSTNode root;
    private int size;

    /**
     * Constructor. Initializes an empty BST with root set to null and size set to 0.
     */
    public BST() {
        root = null;
        size = 0;
    }


    /**
     * Performs an in-order traversal of the BST and records indices and data values.
     */
    private String inOrderTraversal(BSTNode node) {
        StringBuilder sb = new StringBuilder();
        if (node == null) {
            return "";
        }
        sb.append(inOrderTraversal(node.left));
        sb.append(node.toString());
        sb.append(inOrderTraversal(node.right));
        return sb.toString();
    }

    /**
     * Returns a string representation of the entire BST using in-order traversal.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("In-order Traversal of the BST ...\n==================\n");
        sb.append(inOrderTraversal(root));
        return sb.toString();
    }

    /**
     * Returns the size of the BST, i.e., the number of valid nodes.
     */
    public int getSize() {
        return size;
    }

    /**
     * Adds a new node with the specified index and data to the BST.
     */
    public void addNode(I _index, T _data) {
        BSTNode newNode = new BSTNode(_index, _data);
        if (root == null) {
            root = newNode;
            size++;
            return;
        }
        addHelper(root, newNode);
        size++;
    }
    private void addHelper (BSTNode currRoot, BSTNode toAdd) {
        int compare = toAdd.getIndex().compareTo(currRoot.getIndex());
        if (compare < 0) {
            if (currRoot.left == null) {
                currRoot.left = toAdd;
            }
            else {
                addHelper(currRoot.left, toAdd);
            }
        }
        else if (compare > 0) {
            if (currRoot.right == null) {
                currRoot.right = toAdd;
            }
            else {
                addHelper(currRoot.right, toAdd);
            }
        }
    }

    /**
     * Searches for a node with the specified index in the BST.
     */
    public BSTNode searchNode(I _index) {
        return searchHelper(root, _index);
    }

    private BSTNode searchHelper(BSTNode currRoot, I _index) {
        if (currRoot == null) {
            return null;
        }

        int compare = _index.compareTo(currRoot.getIndex());
        if (compare < 0) {
            return searchHelper(currRoot.left, _index);
        } else if (compare > 0) {
            return searchHelper(currRoot.right, _index);
        } else {
            return currRoot;
        }
    }
    /**
     * Removes a node with the specified index from the BST.
     */
    public void removeNode(I _index) throws IllegalArgumentException {
        root = removeHelper(root, _index);
    }

    private BSTNode removeHelper(BSTNode currRoot, I _index) {
        if (currRoot == null) {
            throw new IllegalArgumentException("removeNode(I _index): No node with an index " + _index + " in the BST");
        }
        int compare = _index.compareTo(currRoot.getIndex());
        if (compare < 0) {
            currRoot.left = removeHelper(currRoot.left, _index);
        } else if (compare > 0) {
            currRoot.right = removeHelper(currRoot.right, _index);
        } else {
            size--;
            if (currRoot.left == null && currRoot.right == null) {
                return null;
            } else if (currRoot.left == null) {
                return currRoot.right;
            } else if (currRoot.right == null) {
                return currRoot.left;
            } else {
                BSTNode successor = findMin(currRoot.right);
                currRoot.index = successor.index;
                currRoot.data = successor.data;
                currRoot.right = removeHelper(currRoot.right, successor.index);
                size++;
            }
        }
        return currRoot;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Updates a node's data with a new value, given its index.
     */
    public void updateNode(I _index, T _newData) {
        BSTNode targetNode = searchNode(_index);
        if (targetNode == null) {
            throw new IllegalArgumentException("updateNode(I _index, T _newData): No node with an index " + _index + " in the BST");
        }
        targetNode.setData(_newData);
    }

    
/************************************ GRADING CODE (DO NOT MODIFY) ************************************ */
    /**
     * Performs a pre-order traversal of the BST.
     */
    private void preOrderTraversal(BSTNode node, int[] idx, String[] arr, boolean dataFlag) {
        // DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        if(node == null)
            return;

        if(dataFlag)
            arr[idx[0]] = String.valueOf(node.getData());
        else
            arr[idx[0]] = String.valueOf(node.getIndex());
        idx[0]++;
        
        preOrderTraversal(node.left, idx, arr, dataFlag);
        preOrderTraversal(node.right, idx, arr, dataFlag);
    }

    /**
     * Returns an array of data values in pre-order traversal order.
     * @return A String array containing the data values of all nodes in pre-order order
     */
    public String[] getDataArray() {
        /// DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        String[] dataArr = new String[size];
        preOrderTraversal(this.root, new int[1], dataArr, true);
        return dataArr;
    }

    /**
     * Returns an array of index values in pre-order traversal order.
     * @return A String array containing the index values of all nodes in pre-order order
     */
    public String[] getIndexArray() {
        // DO NOT CHANGE THIS. THIS FOR TESTING PURPOSES
        String[] indexArr = new String[size];
        preOrderTraversal(this.root, new int[1], indexArr, false);
        return indexArr;
    }

/****************************************************************************************************** */

}