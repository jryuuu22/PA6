import java.util.ArrayList;


/**
 * Binary Search Tree of string keys that tracks duplicate insertions.
 *
 * @author Jay Wu
 * @since 11/11/2026
 */

public class BSTree {

    private int nelems;
    private BSTNode root;

    protected class BSTNode {

        String key;
        int count;
        BSTNode left;
        BSTNode right;

        public BSTNode(BSTNode left, BSTNode right, String key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.count = 0;
        }

        public String getKey() {
            return key;
        }

        public BSTNode getLeft() {
            return left;
        }

        public BSTNode getRight() {
            return right;
        }

        public int getNodeCount() {
            return count;
        }

        public void setLeft(BSTNode newleft) {
            left = newleft;
        }

        public void setRight(BSTNode newright) {
            right = newright;
        }
    }

    public BSTree() {
        root = null;
        nelems = 0;
    }

    public BSTNode getRoot() {
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

    private BSTNode insertHelper(BSTNode node, String key) {
        if (node == null) {
            nelems++;
            return new BSTNode(null, null, key);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insertHelper(node.left, key);
        } else if (cmp > 0) {
            node.right = insertHelper(node.right, key);
        } else {
            node.count++;
        }
        return node;
    }

    public boolean findKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return findHelper(root, key);
    }

    private boolean findHelper(BSTNode node, String key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return findHelper(node.left, key);
        } else if (cmp > 0) {
            return findHelper(node.right, key);
        } else {
            return true;
        }
    }

    public void inOrderTraversal(String[] keyArray) {
        if (root == null) {
            throw new NullPointerException();
        }
        inOrderHelper(root, keyArray, 0);
    }

    private int inOrderHelper(BSTNode node, String[] keyArray, int index) {
        if (node == null) {
            return index;
        }
        index = inOrderHelper(node.left, keyArray, index);
        keyArray[index] = node.key;
        return inOrderHelper(node.right, keyArray, index + 1);
    }

    public int findHeight() {
        return heightHelper(root);
    }

    private int heightHelper(BSTNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
    }

    public int leafCount() {
        return leafHelper(root);
    }

    private int leafHelper(BSTNode node) {
        if (node == null) {
            return 0;
        }
        if (node.left == null && node.right == null) {
            return 1;
        }
        return leafHelper(node.left) + leafHelper(node.right);
    }

    // Part 5 extra credit
    public ArrayList<String> intersection(BSTree tree1, BSTree tree2) {
        ArrayList<String> keys1 = new ArrayList<>();
        ArrayList<String> keys2 = new ArrayList<>();
        collectKeys(tree1.root, keys1);
        collectKeys(tree2.root, keys2);

        ArrayList<String> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < keys1.size() && j < keys2.size()) {
            int cmp = keys1.get(i).compareTo(keys2.get(j));
            if (cmp == 0) {
                result.add(keys1.get(i));
                i++;
                j++;
            } else if (cmp < 0) {
                i++;
            } else {
                j++;
            }
        }
        return result;
    }

    private void collectKeys(BSTNode node, ArrayList<String> keys) {
        if (node == null) {
            return;
        }
        collectKeys(node.left, keys);
        keys.add(node.key);
        collectKeys(node.right, keys);
    }

    // Part 5 extra credit
    public String levelMax(int level) {
        return levelMaxHelper(root, level);
    }

    private String levelMaxHelper(BSTNode node, int level) {
        if (node == null) {
            return null;
        }
        if (level == 0) {
            return node.key;
        }
        String rightMax = levelMaxHelper(node.right, level - 1);
        if (rightMax != null) {
            return rightMax;
        }
        return levelMaxHelper(node.left, level - 1);
    }
}

