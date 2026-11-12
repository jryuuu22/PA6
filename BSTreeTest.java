import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BSTreeTest {

    private BSTree empty;
    private BSTree tree;   // m, d, r, b, f, p, x
    private BSTree single; // k

    @BeforeEach
    public void setUp() {
        empty = new BSTree();

        tree = new BSTree();
        for (String s : new String[]{"m", "d", "r", "b", "f", "p", "x"}) {
            tree.insert(s);
        }

        single = new BSTree();
        single.insert("k");
    }


    @Test
    public void testNodeConstructorAndGetKey() {
        BSTree.BSTNode leftNode = empty.new BSTNode(null, null, "a");
        BSTree.BSTNode rightNode = empty.new BSTNode(null, null, "c");
        BSTree.BSTNode node = empty.new BSTNode(leftNode, rightNode, "b");
        assertEquals("b", node.getKey());
        assertEquals("a", leftNode.getKey());
        assertEquals("c", rightNode.getKey());
    }

    @Test
    public void testNodeGetLeft() {
        BSTree.BSTNode leftNode = empty.new BSTNode(null, null, "a");
        BSTree.BSTNode node = empty.new BSTNode(leftNode, null, "b");
        assertSame(leftNode, node.getLeft());
        assertNull(leftNode.getLeft());
        assertEquals("d", tree.getRoot().getLeft().getKey());
    }

    @Test
    public void testNodeGetRight() {
        BSTree.BSTNode rightNode = empty.new BSTNode(null, null, "c");
        BSTree.BSTNode node = empty.new BSTNode(null, rightNode, "b");
        assertSame(rightNode, node.getRight());
        assertNull(rightNode.getRight());
        assertEquals("r", tree.getRoot().getRight().getKey());
    }

    @Test
    public void testNodeGetNodeCount() {
        assertEquals(0, single.getRoot().getNodeCount());
        single.insert("k");
        assertEquals(1, single.getRoot().getNodeCount());
        single.insert("k");
        assertEquals(2, single.getRoot().getNodeCount());
    }

    @Test
    public void testNodeSetLeft() {
        BSTree.BSTNode node = empty.new BSTNode(null, null, "b");
        BSTree.BSTNode newLeft = empty.new BSTNode(null, null, "a");
        node.setLeft(newLeft);
        assertSame(newLeft, node.getLeft());
        node.setLeft(null);
        assertNull(node.getLeft());
        assertNull(node.getRight());
    }

    @Test
    public void testNodeSetRight() {
        BSTree.BSTNode node = empty.new BSTNode(null, null, "b");
        BSTree.BSTNode newRight = empty.new BSTNode(null, null, "c");
        node.setRight(newRight);
        assertSame(newRight, node.getRight());
        node.setRight(null);
        assertNull(node.getRight());
        assertNull(node.getLeft());
    }

    /* ---------------- BSTree ---------------- */

    @Test
    public void testConstructor() {
        BSTree t = new BSTree();
        assertNull(t.getRoot());
        assertEquals(0, t.getSize());
        assertEquals(-1, t.findHeight());
    }

    @Test
    public void testGetRoot() {
        assertNull(empty.getRoot());
        assertEquals("m", tree.getRoot().getKey());
        assertEquals("k", single.getRoot().getKey());
    }

    @Test
    public void testGetSize() {
        assertEquals(0, empty.getSize());
        assertEquals(7, tree.getSize());
        tree.insert("m"); // duplicate does not change size
        assertEquals(7, tree.getSize());
    }

    @Test
    public void testInsert() {
        assertTrue(empty.insert("a"));
        assertEquals("a", empty.getRoot().getKey());
        assertTrue(empty.insert("b"));
        assertEquals("b", empty.getRoot().getRight().getKey());
        assertTrue(empty.insert("a")); // duplicate still returns true
        assertEquals(2, empty.getSize());
    }

    @Test
    public void testInsertPlacement() {
        assertEquals("b", tree.getRoot().getLeft().getLeft().getKey());
        assertEquals("f", tree.getRoot().getLeft().getRight().getKey());
        assertEquals("p", tree.getRoot().getRight().getLeft().getKey());
        assertEquals("x", tree.getRoot().getRight().getRight().getKey());
    }

    @Test
    public void testInsertNull() {
        assertThrows(NullPointerException.class, () -> empty.insert(null));
        assertThrows(NullPointerException.class, () -> tree.insert(null));
        assertThrows(NullPointerException.class, () -> single.insert(null));
    }

    @Test
    public void testFindKey() {
        assertTrue(tree.findKey("m"));
        assertTrue(tree.findKey("x"));
        assertTrue(tree.findKey("b"));
        assertFalse(tree.findKey("z"));
        assertFalse(empty.findKey("a"));
        assertFalse(single.findKey("K")); // case sensitive
    }

    @Test
    public void testFindKeyNull() {
        assertThrows(NullPointerException.class, () -> empty.findKey(null));
        assertThrows(NullPointerException.class, () -> tree.findKey(null));
        assertThrows(NullPointerException.class, () -> single.findKey(null));
    }

    @Test
    public void testInOrderTraversal() {
        String[] arr = new String[7];
        tree.inOrderTraversal(arr);
        assertArrayEquals(new String[]{"b", "d", "f", "m", "p", "r", "x"}, arr);

        String[] one = new String[1];
        single.inOrderTraversal(one);
        assertArrayEquals(new String[]{"k"}, one);

        tree.insert("m");
        tree.insert("b"); // duplicates appear once
        String[] dup = new String[7];
        tree.inOrderTraversal(dup);
        assertArrayEquals(new String[]{"b", "d", "f", "m", "p", "r", "x"}, dup);
    }

    @Test
    public void testInOrderTraversalStringOrder() {
        BSTree nums = new BSTree();
        for (String s : new String[]{"4", "10", "7", "9"}) {
            nums.insert(s);
        }
        String[] arr = new String[4];
        nums.inOrderTraversal(arr);
        assertArrayEquals(new String[]{"10", "4", "7", "9"}, arr); // lexicographic
    }

    @Test
    public void testInOrderTraversalEmpty() {
        assertThrows(NullPointerException.class,
                () -> empty.inOrderTraversal(new String[5]));
        assertThrows(NullPointerException.class,
                () -> new BSTree().inOrderTraversal(new String[0]));
        assertThrows(NullPointerException.class,
                () -> new BSTree().inOrderTraversal(new String[1]));
    }

    @Test
    public void testFindHeight() {
        assertEquals(-1, empty.findHeight());
        assertEquals(0, single.findHeight());
        assertEquals(2, tree.findHeight());
        tree.insert("y");
        tree.insert("z");
        assertEquals(4, tree.findHeight());
    }

    @Test
    public void testFindHeightLinear() {
        BSTree line = new BSTree();
        for (String s : new String[]{"a", "b", "c", "d"}) {
            line.insert(s);
        }
        assertEquals(3, line.findHeight());
        line.insert("a"); // duplicate does not add height
        assertEquals(3, line.findHeight());
        line.insert("e");
        assertEquals(4, line.findHeight());
    }

    @Test
    public void testLeafCount() {
        assertEquals(0, empty.leafCount());
        assertEquals(1, single.leafCount());
        assertEquals(4, tree.leafCount());
        tree.insert("a"); // b stops being a leaf, a becomes one
        assertEquals(4, tree.leafCount());
        tree.insert("e"); // e under f
        assertEquals(4, tree.leafCount());
        tree.insert("g"); // g under f, f has two children now
        assertEquals(5, tree.leafCount());
    }

    /* ---------------- Extra credit ---------------- */

    @Test
    public void testIntersection() {
        BSTree other = new BSTree();
        for (String s : new String[]{"f", "a", "x", "m", "z"}) {
            other.insert(s);
        }
        assertEquals(new ArrayList<>(Arrays.asList("f", "m", "x")),
                tree.intersection(tree, other));
        assertEquals(new ArrayList<>(),
                tree.intersection(tree, empty));
        assertEquals(new ArrayList<>(Arrays.asList("b", "d", "f", "m", "p", "r", "x")),
                tree.intersection(tree, tree));
    }

    @Test
    public void testLevelMax() {
        assertEquals("m", tree.levelMax(0));
        assertEquals("r", tree.levelMax(1));
        assertEquals("x", tree.levelMax(2));
        assertNull(tree.levelMax(3));
        assertNull(empty.levelMax(0));
    }

    @Test
    public void testLevelMaxOnlyLeftAtLevel() {
        BSTree t = new BSTree();
        for (String s : new String[]{"m", "d", "r", "b"}) {
            t.insert(s);
        }
        assertEquals("b", t.levelMax(2)); // only node at level 2 is on the left
        assertEquals("r", t.levelMax(1));
        assertNull(t.levelMax(5));
    }
}