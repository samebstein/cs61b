package bearmaps;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ <T> {
    private ArrayList<PriorityNode> heap;
    private HashMap<T, Integer> hashMap;
    private int index;


    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        hashMap = new HashMap();
        index = 1;
        heap.add(0, null);
    }


    /** Part of the add function that allows a node to swim up a heap.
     * will use recursion to determine how far it swims. */
    void swim(int k) {
        if (parent(k) == 0 || heap.get(k).compareTo(heap.get(parent(k))) > 0 || k <= 1 ||
                heap.get(k).compareTo(heap.get(parent(k))) == 0) {
            return;
        }
        swap(k, parent(k));
        swim(parent(k));
    }

    int parent(int k) {
        return k / 2;
    }

    void swap(int kIndex, int parentIndex) {
        hashMap.put(heap.get(kIndex).item, parentIndex);
        hashMap.put(heap.get(parentIndex).item, kIndex);

        PriorityNode temp = heap.get(kIndex);
        PriorityNode tempOther = heap.get(parentIndex);
        heap.set(kIndex, tempOther);
        heap.set(parentIndex, temp);
    }


    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("PQ already has" + item);
        }
        heap.add(index, new PriorityNode(item, priority));
        hashMap.put(item, index);
        swim(index);
        index += 1;
    }


    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return hashMap.containsKey(item);
    }


    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        if (hashMap.size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return heap.get(1).item;
    }


    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        if (hashMap.size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        T temp = heap.get(1).item;
        if (hashMap.size() == 1) {
            index -= 1;
            heap.set(index, null);
            hashMap.remove(temp);
            return temp;
        }
        heap.set(1, heap.get(index - 1));
        heap.set(index - 1, null);
        hashMap.put(heap.get(1).item, 1);
        index -= 1;
        sink(1);
        heap.set(index, null);
        hashMap.remove(temp);
        return temp;
    }

    /** Part of the remove smallest function that allows the first node to sink down to it's
     * rightful spot. */
    void sink(int k) {
        if (leftChild(k) >= index) { return; }
        if (rightChild(k) >= index) {
            if (heap.get(k).getPriority() > heap.get(leftChild(k)).getPriority()){
                swap(k, leftChild(k));
                return;
            }
        }
        PriorityNode leftChildNode = heap.get(leftChild(k));
        PriorityNode rightChildNode = heap.get(rightChild(k));
        PriorityNode parent = heap.get(k);
        int leftRightCompare = heap.get(leftChild(k)).compareTo(heap.get(rightChild(k)));

        if (leftRightCompare < 0 && parent.getPriority() > leftChildNode.getPriority()) {
                swap(k, leftChild(k));
                sink(leftChild(k));
        } else if (leftRightCompare > 0 && parent.getPriority() > rightChildNode.getPriority()) {
                swap(k, rightChild(k));
                sink(rightChild(k));
        } else if (leftRightCompare == 0 && parent.getPriority() > leftChildNode.getPriority()) {
            swap(k, leftChild(k));
            sink(leftChild(k));
        } else {
            return;
        }
    }


    /** Returns left child index of element at index k*/
    int leftChild(int k) {
        return k * 2;
    }

    /** Returns right child index of element at index k */
    int rightChild(int k) {
        return k * 2 + 1;
    }


    /* Returns the number of items in the PQ. */
    public int size() {
        return hashMap.size();
    }


    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist.
      * After you change priority, might have to sink or swim.*/
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain" + item);
        }

        // After I find the index of the original item, I change the priority of that node.
        // Then I call sink or swim
        int indexOfInterest = hashMap.get(item);
        heap.get(indexOfInterest).priority = priority;
        sink(indexOfInterest);
        swim(indexOfInterest);
    }


    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        public PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }

        public T getItem() {
            return item;
        }

        public double getPriority() {
            return priority;
        }

        /** Returns 0 if this priority and other priority are equal, value < 0 if this < other,
         * value > 0 if this > other */
        @Override
        public int compareTo(PriorityNode other) {
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            }
            return ((PriorityNode) o).getItem().equals(this.getItem());
        }

        @Override
        public int hashCode() { return item.hashCode(); }
    }
}
