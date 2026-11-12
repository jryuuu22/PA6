import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class orderStatTreeTest {

    private orderStatTree empty;
    private orderStatTree tree;   // spec example: bee, dog, cat, ant, eel, dog
    private orderStatTree single; // k

    @BeforeEach
    public void setUp() {
        empty = new orderStatTree();

        tree = new orderStatTree();
        for (String s : new String[]{"bee", "dog", "cat", "ant", "eel", "dog"}) {
            tree.insert(s);
        }

        single = new orderStatTree();
        single.insert("k");
    }

    /* ---------------- orderStatNode ---------------- */

    @Test
    public void testNodeConstructorAndGetKey() {
        orderStatTree.orderStatNode a = empty.new orderStatNode(null, null, "a");
        orderStatTree.orderStatNode c = empty.new orderStatNode(null, null, "c");
        orderStatTree.orderStatNode b = empty.new orderStatNode(a, c, "b");
        assertEquals("a", a.getKey());
        assertEquals("b", b.getKey());
        assertEquals("c", c.getKey());
    }

    @Test
    public void testNodeGetLeft() {
        orderStatTree.orderStatNode a = empty.new orderStatNode(null, null, "a");
        orderStatTree.orderStatNode b = empty.new orderStatNode(a, null, "b");
        assertSame(a, b.getLeft());
        assertNull(a.getLeft());
        assertEquals("ant", tree.getRoot().getLeft().getKey());
    }

    @Test
    public void testNodeGetRight() {
        orderStatTree.orderStatNode c = empty.new orderStatNode(null, null, "c");
        orderStatTree.orderStatNode b = empty.new orderStatNode(null, c, "b");
        assertSame(c, b.getRight());
        assertNull(c.getRight());
        assertEquals("dog", tree.getRoot().getRight().getKey());
    }

    @Test
    public void testNodeGetNodeCount() {
        assertEquals(0, single.getRoot().getNodeCount());
        assertEquals(1, tree.getRoot().getRight().getNodeCount()); // dog twice
        single.insert("k");
        assertEquals(1, single.getRoot().getNodeCount());
    }

    @Test
    public void testNodeSetLeft() {
        orderStatTree.orderStatNode b = empty.new orderStatNode(null, null, "b");
        orderStatTree.orderStatNode a = empty.new orderStatNode(null, null, "a");
        b.setLeft(a);
        assertSame(a, b.getLeft());
        b.setLeft(null);
        assertNull(b.getLeft());
        assertNull(b.getRight());
    }

    @Test
    public void testNodeSetRight() {
        orderStatTree.orderStatNode b = empty.new orderStatNode(null, null, "b");
        orderStatTree.orderStatNode c = empty.new orderStatNode(null, null, "c");
        b.setRight(c);
        assertSame(c, b.getRight());
        b.setRight(null);
        assertNull(b.getRight());
        assertNull(b.getLeft());
    }

    /* ---------------- orderStatTree ---------------- */

    @Test
    public void testConstructor() {
        orderStatTree t = new orderStatTree();
        assertNull(t.getRoot());
        assertEquals(0, t.getSize());
        assertFalse(t.findKey("a"));
    }

    @Test
    public void testGetRoot() {
        assertNull(empty.getRoot());
        assertEquals("bee", tree.getRoot().getKey());
        assertEquals("k", single.getRoot().getKey());
    }

    @Test
    public void testGetSize() {
        assertEquals(0, empty.getSize());
        assertEquals(1, single.getSize());
        assertEquals(5, tree.getSize()); // duplicate dog not counted
    }

    @Test
    public void testInsert() {
        assertTrue(empty.insert("m"));
        assertTrue(empty.insert("a"));
        assertTrue(empty.insert("m"));
        assertEquals(2, empty.getSize());
        assertEquals("a", empty.getRoot().getLeft().getKey());
    }

    @Test
    public void testInsertNull() {
        assertThrows(NullPointerException.class, () -> empty.insert(null));
        assertThrows(NullPointerException.class, () -> tree.insert(null));
        assertThrows(NullPointerException.class, () -> single.insert(null));
    }

    @Test
    public void testFindKey() {
        assertTrue(tree.findKey("bee"));
        assertTrue(tree.findKey("eel"));
        assertTrue(tree.findKey("cat"));
        assertFalse(tree.findKey("fox"));
        assertFalse(empty.findKey("bee"));
    }

    @Test
    public void testFindKeyNull() {
        assertThrows(NullPointerException.class, () -> empty.findKey(null));
        assertThrows(NullPointerException.class, () -> tree.findKey(null));
        assertThrows(NullPointerException.class, () -> single.findKey(null));
    }

    @Test
    public void testGetSubtreeSize() {
        assertEquals(5, tree.getSubtreeSize("bee"));
        assertEquals(3, tree.getSubtreeSize("dog"));
        assertEquals(1, tree.getSubtreeSize("ant"));
        assertEquals(1, tree.getSubtreeSize("cat"));
        assertEquals(1, single.getSubtreeSize("k"));
    }

    @Test
    public void testGetSubtreeSizeAfterInsert() {
        tree.insert("fox"); // goes under eel
        assertEquals(6, tree.getSubtreeSize("bee"));
        assertEquals(4, tree.getSubtreeSize("dog"));
        assertEquals(2, tree.getSubtreeSize("eel"));
    }

    @Test
    public void testGetSubtreeSizeExceptions() {
        assertThrows(NullPointerException.class, () -> tree.getSubtreeSize(null));
        assertThrows(IllegalArgumentException.class, () -> tree.getSubtreeSize("fox"));
        assertThrows(IllegalArgumentException.class, () -> empty.getSubtreeSize("a"));
    }

    @Test
    public void testGetPercentile() {
        assertEquals("cat", tree.getPercentile(50));
        assertEquals("bee", tree.getPercentile(23));
        assertEquals("ant", tree.getPercentile(0));
        assertEquals("eel", tree.getPercentile(100));
        assertEquals("ant", tree.getPercentile(20));
        assertEquals("bee", tree.getPercentile(21));
    }

    @Test
    public void testGetPercentileSingle() {
        assertEquals("k", single.getPercentile(0));
        assertEquals("k", single.getPercentile(50));
        assertEquals("k", single.getPercentile(100));
    }

    @Test
    public void testGetPercentileInvalid() {
        assertThrows(IllegalArgumentException.class, () -> tree.getPercentile(-1));
        assertThrows(IllegalArgumentException.class, () -> tree.getPercentile(101));
        assertThrows(IllegalArgumentException.class, () -> empty.getPercentile(200));
    }

    @Test
    public void testGetPercentileEmpty() {
        assertThrows(IllegalStateException.class, () -> empty.getPercentile(0));
        assertThrows(IllegalStateException.class, () -> empty.getPercentile(50));
        assertThrows(IllegalStateException.class, () -> empty.getPercentile(100));
    }

    @Test
    public void testMedian() {
        assertEquals("cat", tree.median());
        assertEquals("k", single.median());
        tree.insert("fox"); // 6 keys: ceil(3) = 3rd
        assertEquals("cat", tree.median());
        tree.insert("gnu"); // 7 keys: ceil(3.5) = 4th
        assertEquals("dog", tree.median());
    }

    @Test
    public void testMedianEmpty() {
        assertThrows(IllegalStateException.class, () -> empty.median());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().median());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().median());
    }

    @Test
    public void testMin() {
        assertEquals("ant", tree.min());
        assertEquals("k", single.min());
        tree.insert("aardvark");
        assertEquals("aardvark", tree.min());
    }

    @Test
    public void testMinEmpty() {
        assertThrows(IllegalStateException.class, () -> empty.min());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().min());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().min());
    }

    @Test
    public void testMax() {
        assertEquals("eel", tree.max());
        assertEquals("k", single.max());
        tree.insert("zebra");
        assertEquals("zebra", tree.max());
    }

    @Test
    public void testMaxEmpty() {
        assertThrows(IllegalStateException.class, () -> empty.max());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().max());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().max());
    }
}
