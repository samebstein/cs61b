import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        tas.enqueue("Sam");
        tas.enqueue("Jared");
        tas.enqueue("James");
        tas.enqueue("Nari");


        List<String> arr = new ArrayList<>();
        Queue<String> actual = QuickSort.quickSort(tas);
        while (!actual.isEmpty()) {
            arr.add(actual.dequeue());
        }

        for (int i = 0; i < arr.size() - 1; i++) {
            assertTrue(arr.get(i).compareTo(arr.get(i + 1)) <= 0);
        }
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");
        tas.enqueue("Sam");
        tas.enqueue("Jared");
        tas.enqueue("James");
        tas.enqueue("Nari");


        List<String> arr = new ArrayList<>();
        Queue<String> actual = MergeSort.mergeSort(tas);
        while (!actual.isEmpty()) {
            arr.add(actual.dequeue());
        }

        for (int i = 0; i < arr.size() - 1; i++) {
            assertTrue(arr.get(i).compareTo(arr.get(i + 1)) <= 0);
        }
    }
        @Test
        public void testMergeSort2() {
        Queue<Integer> tas = new Queue<Integer>();
        tas.enqueue(1);
        tas.enqueue(8);
        tas.enqueue(3);
        tas.enqueue(4);
        tas.enqueue(17);
        tas.enqueue(1);

        List<Integer> arr = new ArrayList<>();
        Queue<Integer> actual = MergeSort.mergeSort(tas);
        while (!actual.isEmpty()) {
            arr.add(actual.dequeue());
        }

        for (int i = 0; i < arr.size() - 1; i++) {
            assertTrue(arr.get(i).compareTo(arr.get(i + 1)) <= 0);
        }


    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
