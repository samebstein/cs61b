package hw2;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestPercolation {

    @Test
    public void testPerc() {
        Percolation p = new Percolation(4);
        assertFalse(p.legitimatePlus1(0));
        assertFalse(p.legitimatePlus1(4));
        assertFalse(p.legitimatePlus1(8));
        assertFalse(p.legitimatePlus1(12));
        assertTrue(p.legitimatePlus1(3));
        assertTrue(p.legitimatePlus1(7));
        assertTrue(p.legitimatePlus1(15));
        assertFalse(p.legitimateMinus1(11));
        assertFalse(p.legitimateMinus1(3));
        System.out.println(8 / 4);
        System.out.println(11 / 4);
        System.out.println(7 / 4);

    }

    @Test
    public void testPerc2() {
        Percolation p5 = new Percolation(5);
        assertFalse(p5.isFull(4, 4));
        assertFalse(p5.isFull(0, 0));
        assertFalse(p5.isOpen(4, 4));
        assertFalse(p5.isOpen(0, 0));
        assertFalse(p5.percolates());
        p5.open(4, 4);
        p5.open(3, 4);
        p5.open(3, 3);
        p5.open(3, 2);
        p5.open(2, 2);
        p5.open(1, 2);
        p5.open(1, 1);
        assertFalse(p5.isFull(4, 4));
        p5.open(0, 1);
        assertTrue(p5.wuf.connected(24, 19));
        assertTrue(p5.isFull(4, 4));
        assertTrue(p5.percolates());
        assertTrue(p5.isFull(4, 4));
        assertTrue(p5.isFull(3,4));
        assertTrue(p5.isFull(3, 3));
        p5.open(4, 2);
        assertTrue(p5.isFull(4, 2));
    }

    @Test
    public void testPerc3() {
        Percolation p3 = new Percolation(3);
        assertFalse(p3.isFull(0, 0));
        assertFalse(p3.percolates());
        p3.open(0,2);
        assertFalse(p3.isFull(0, 0));
        p3.open(1,0);
        p3.open(1,1);
        p3.open(1,2);
        p3.open(2,0);
        assertTrue(p3.isFull(2, 0));
        assertTrue(p3.percolates());
    }
}
