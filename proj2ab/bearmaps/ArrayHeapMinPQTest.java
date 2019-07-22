package bearmaps;

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
}
