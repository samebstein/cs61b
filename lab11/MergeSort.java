import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /**
     * Returns a queue of queues that each contain one item from items.
     *
     * This method should take linear time.
     *
     * @param   items  A Queue of items.
     * @return         A Queue of queues, each containing an item from items.
     *
     *    /** This method takes in a Queue called items, and should return a Queue of Queues that
     *      * each contain one item from items. For example, if you called makeSingleItemQueues on the
     *      * Queue "(Joe" -> "Omar" -> "Itai"), it should return (("Joe") -> ("Omar") -> ("Itai")).*/

    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> queueQueueItems = new Queue<>();
        while (!items.isEmpty()) {
            Queue<Item> q = new Queue<>();
            q.enqueue(items.dequeue());
            queueQueueItems.enqueue(q);
        }
        return queueQueueItems;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     * This method takes two sorted queues q1 and q2 as parameters, and returns
     * a new queue that has all of the items in q1 and q2 in sorted order.
     * For example, mergeSortedQueues(("Itai" -> "Omar"), ("Joe")) should
     * return ("Itai" -> "Joe" -> "Omar").
     * The provided getMin helper method may be helpful in implementing mergeSortedQueues. Your implementation should take time linear in the total number of items in q1
     * and q2. In other words, your implementation should be Θ(q1.size() + q2.size()).
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> q = new Queue<>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            Item min = MergeSort.getMin(q1, q2);
            q.enqueue(min);
        }

        if (!q1.isEmpty()) {
            while (!q1.isEmpty()) {
                q.enqueue(q1.dequeue());
            }
        }

        if (!q2.isEmpty()) {
            while (!q2.isEmpty()) {
                q.enqueue(q2.dequeue());
            }
        }
        return q;
    }

    /**
     * Returns a Queue that contains the given items sorted from least to greatest.
     *
     * This method should take roughly nlogn time where n is the size of "items"
     * this method should be non-destructive and not empty "items".
     *
     * @param   items  A Queue to be sorted.
     * @return         A Queue containing every item in "items".
     *
     *
     * Steps: recursive function, so need base case. If a queue is of size 1, its sorted,
     * so return that queue. Otherwise:
     * 1) Split the queue of items into two equal (sometimes just off equal) queues of items.
     * 2) Call Mergesort on the left, and mergesort on the right. each will return queue of sorted items.
     * 3) Then
     *
     *
     */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> singleItemQueueOfQueues = makeSingleItemQueues(items);

        return mergeSortHelper(singleItemQueueOfQueues);
    }


    private static <Item extends Comparable> Queue<Item>
                    mergeSortHelper(Queue<Queue<Item>> singleItemQueueOfQueues) {
        int size = singleItemQueueOfQueues.size();
        Queue<Item> q;
        Queue<Queue<Item>> left = new Queue<>();
        Queue<Queue<Item>> right = new Queue<>();

       if (size == 2) {
           q = mergeSortedQueues(singleItemQueueOfQueues.dequeue(), singleItemQueueOfQueues.dequeue());
           return q;
       } else if (size == 1) {
           return singleItemQueueOfQueues.dequeue();
       } else {
           for (int i = 0; i < size / 2; i++) {
               left.enqueue(singleItemQueueOfQueues.dequeue());
           }
           for (int i = 0; i < (size - size/2); i++) {
               right.enqueue(singleItemQueueOfQueues.dequeue());
           }
       }
       Queue<Item> leftSorted = mergeSortHelper(left);
       Queue<Item> rightSorted = mergeSortHelper(right);
       q = mergeSortedQueues(leftSorted, rightSorted);
       return q;
    }

}
