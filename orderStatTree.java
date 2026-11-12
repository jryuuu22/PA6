/**
 * An order-statistic tree: BST of string keys where each node stores the
 * size of subtree, allowing keys to be looked up by percentile.
 *
 * @author Jay Wu
 * @since 11/11/2026
 */

public class orderStatTree {
    private static final int MAX_PERCENTILE = 100;
    private static final int MEDIAN_PERCENTILE = 50;
    private int nelems;
    private orderStatNode root;

    protected class orderStatNode {

        String key;
        int keyCount;
        int subtreeSize;
        orderStatNode left;
        orderStatNode right;

        public orderStatNode(orderStatNode left, orderStatNode right,
                             String key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.keyCount = 0;
            this.subtreeSize = 1 + sizeOf(left) + sizeOf(right);
        }

        public String getKey() {
            return key;
        }

        public orderStatNode getLeft() {
            return left;
        }

        public orderStatNode getRight() {
            return right;
        }

        public int getNodeCount() {
            return keyCount;
        }

        public void setLeft(orderStatNode newleft) {
            left = newleft;
        }

        public void setRight(orderStatNode newright) {
            right = newright;
        }
    }

    public orderStatTree() {
        root = null;
        nelems = 0;
    }

    public orderStatNode getRoot() {
        return root;
    }

    public int getSize() {
        return nelems;
    }

    public boolean insert(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = insertHelper(root, key);
        return true;
    }

    private orderStatNode insertHelper(orderStatNode node, String key) {
        if (node == null) {
            nelems++;
            return new orderStatNode(null, null, key);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insertHelper(node.left, key);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, key);
        } else {
            node.keyCount++;
        }
        node.subtreeSize = 1 + sizeOf(node.left) + sizeOf(node.right);
        return node;
    }

    public boolean findKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return findNode(root, key) != null;
    }

    private orderStatNode findNode(orderStatNode node, String key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return findNode(node.left, key);
        } else if (cmp > 0) {
            return findNode(node.right, key);
        }
        return node;
    }

    public int getSubtreeSize(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        orderStatNode node = findNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException();
        }
        return node.subtreeSize;
    }

    public String getPercentile(int percentile) {
        if (percentile < 0 || percentile > MAX_PERCENTILE) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            throw new IllegalStateException();
        }
        // k = ceil(percentile / 100 * n), using integer math; 0th maps to 1st
        int rank = (percentile * nelems + MAX_PERCENTILE - 1) / MAX_PERCENTILE;
        rank = Math.max(rank, 1);
        return selectHelper(root, rank);
    }

    private String selectHelper(orderStatNode node, int rank) {
        int leftSize = sizeOf(node.left);
        if (rank <= leftSize) {
            return selectHelper(node.left, rank);
        } else if (rank == leftSize + 1) {
            return node.key;
        }
        return selectHelper(node.right, rank - leftSize - 1);
    }

    public String median() {
        return getPercentile(MEDIAN_PERCENTILE);
    }

    public String min() {
        return getPercentile(0);
    }

    public String max() {
        return getPercentile(MAX_PERCENTILE);
    }

    private int sizeOf(orderStatNode node) {
        if (node == null) {
            return 0;
        }
        return node.subtreeSize;
    }
}