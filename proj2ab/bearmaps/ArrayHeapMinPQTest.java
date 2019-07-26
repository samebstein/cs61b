package bearmaps;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    @Test
    public void sanityGenericTest() {
        ArrayHeapMinPQ<String> initial = new ArrayHeapMinPQ<>();
        initial.add("sam", 1);
        String expected = initial.getSmallest();
        String actual = "sam";
        assertEquals(expected, actual);

    }

    @Test
    public void addTest() {
        ArrayHeapMinPQ<String> initial1 = new ArrayHeapMinPQ<>();
        initial1.add("sam", 2);
        initial1.add("jared", 3);
        initial1.add("james", 4);
        initial1.add("nari", 1);
        String expected = initial1.getSmallest();
        String actual = "nari";
        assertEquals(expected, actual);
    }

    @Test
    public void removeSmallestTest() {
        ArrayHeapMinPQ<String> initial2 = new ArrayHeapMinPQ<>();
        initial2.add("sam", 2);
        initial2.add("jared", 3);
        initial2.add("james", 4);
        initial2.add("nari", 1);
        initial2.removeSmallest();
        assertEquals(initial2.getSmallest(), "sam");
        initial2.removeSmallest();
        assertEquals(initial2.getSmallest(), "jared");

        initial2.removeSmallest();
        assertEquals(initial2.getSmallest(), "james");
        initial2.removeSmallest();
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<String> initial3 = new ArrayHeapMinPQ<>();
        initial3.add("sam", 2);
        initial3.add("jared", 3);
        initial3.add("james", 4);
        initial3.add("nari", 1);
        initial3.changePriority("jared", 0);
        assertEquals(initial3.getSmallest(), "jared");
        initial3.changePriority("jared", 10);
        assertEquals(initial3.getSmallest(), "nari");
        initial3.changePriority("nari", 2);
        assertEquals(initial3.getSmallest(), "nari");

    }

    @Test
    public void containsTest() {
        ArrayHeapMinPQ<String> initial2 = new ArrayHeapMinPQ<>();
        initial2.add("sam", 2);
        initial2.add("jared", 3);
        initial2.add("james", 4);
        initial2.add("nari", 1);
        initial2.removeSmallest();
        assertFalse(initial2.contains("nari"));
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<String> initial3 = new ArrayHeapMinPQ<>();
        initial3.add("sam", 2);
        initial3.add("jared", 3);
        initial3.add("james", 4);
        initial3.add("nari", 1);
        for (int i = 0; i < 4; i++){
            initial3.removeSmallest();
        }
    }

    @Test
    public void testTiming() {
        ArrayHeapMinPQ<String> heapMine = new ArrayHeapMinPQ<>();
        for (int i = 1; i < 5; i += 1) {
            heapMine.add("hi" + i, i);
        }

        heapMine.removeSmallest();
        heapMine.removeSmallest();
        heapMine.removeSmallest();
        heapMine.removeSmallest();
        //Stopwatch sw = new Stopwatch();
        //System.out.println("Total time elapsed of my implementation: " + sw.elapsedTime() +  " seconds.");
    }
}
