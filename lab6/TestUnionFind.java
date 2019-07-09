import org.junit.Test;
import static org.junit.Assert.*;

public class TestUnionFind {

    @Test
    public void testBasics() {
        UnionFind u = new UnionFind(5);
        int[] parentExpected = new int[]{-1, -1, -1, -1, -1};
        assertArrayEquals(parentExpected, u.disjointSet);
        assertEquals(u.sizeOfArray, 5);
    }

    @Test
    public void testUnion() {
        UnionFind u1 = new UnionFind(5);
        u1.union(1, 2);
        int root1Expected = 2;
        int root1Actual = u1.disjointSet[1];
        assertEquals(root1Expected, root1Actual);
        int root2Actual = u1.disjointSet[2];
        //This tests size as well.
        assertEquals(-2, root2Actual);

        // Tests union of small tree to bigger tree
        u1.union(0, 1);
        int root0Expected = 2;
        int root0Actual = u1.disjointSet[0];
        assertEquals(root0Expected, root0Actual);


    }

    @Test
    public void testParent(){
        UnionFind u2 = new UnionFind(5);
        u2.union(1, 2);
        u2.union(0, 1);
        assertEquals(2, u2.disjointSet[1]);
        assertEquals(-3, u2.disjointSet[2]);
    }

    @Test
    public void testFind() {
        // Without Path Compression
        /**UnionFind u3 = new UnionFind(6);
        u3.union(4, 5);
        u3.union(2, 3);
        u3.union(1, 2);
        u3.union(0, 4);
        u3.union(2, 0);
        assertEquals(5, u3.disjointSet[3]);

        int findActualInt = u3.find(2);
        int findExpectedInt = 5;
        assertEquals(findExpectedInt, findActualInt);

        int findActualInt1 = u3.find(1);
        int findExpectedInt1 = 5;
        assertEquals(findExpectedInt, findActualInt1);

        int[] findExpectedArray = new int[]{5, 3, 3, 5, 5, -6};
        assertArrayEquals(findExpectedArray, u3.disjointSet);*/

        // With Path Compression
        UnionFind u6 = new UnionFind(6);
        u6.union(4, 5);
        u6.union(2, 3);
        u6.union(1, 2);
        u6.union(0, 4);
        u6.union(2, 0);
        assertEquals(5, u6.disjointSet[3]);

        int findActualIntP = u6.find(2);
        int findExpectedIntP = 5;
        assertEquals(findExpectedIntP, findActualIntP);

        int findActualInt1P = u6.find(1);
        int findExpectedInt1P = 5;
        assertEquals(findExpectedIntP, findActualInt1P);

        int[] findExpectedArrayP = new int[]{5, 5, 5, 5, 5, -6};
        assertArrayEquals(findExpectedArrayP, u6.disjointSet);

    }


    @Test
    public void testSizeOf() {
        UnionFind u4 = new UnionFind(5);
        u4.union(1, 2);
        u4.union(0, 1);
        int expectedSize = 3;
        int actualSize1 = u4.sizeOf(1);
        int actualSize2 = u4.sizeOf(2);
        assertEquals(expectedSize, actualSize1);
        assertEquals(expectedSize, actualSize2);
    }

    @Test
    public void testConnected() {
        UnionFind u5 = new UnionFind(7);
        u5.union(4, 5);
        u5.union(2, 3);
        u5.union(1, 2);
        u5.union(0, 4);
        u5.union(2, 0);
        boolean expecteBool = true;
        boolean actualBool = u5.connected(2, 4);
        assertTrue(actualBool);
        assertEquals(expecteBool, actualBool);
        assertFalse(u5.connected(4, 6));

    }


}
