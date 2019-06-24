public class ArrayDeque<Zaphod> {
    private Zaphod[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int RFACTOR;

    public ArrayDeque() {
        items = (Zaphod[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        RFACTOR = 2;
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        int start = plusOne(nextFirst);
        Zaphod[] a = (Zaphod[]) new Object[capacity];
        if (size == items.length) {
            int firstPart = items.length - start;
            int secondPart = items.length - firstPart;
            System.arraycopy(items, start, a, 0, firstPart);
            System.arraycopy(items, 0, a, firstPart, secondPart);
        } else {
            System.arraycopy(items, start, a, 0, size);
        }
        items = a;
    }

    /** Inserts X into the back of the list. */
    public void addLast(Zaphod x) {
        if(size == items.length) {
            resize(size * RFACTOR);
        }
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size = size + 1;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Adds an item of type Zaphod to the front of the deque */
    public void addFirst(Zaphod x) {
        if(size == items.length) {
            resize(size * RFACTOR);
        }
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size = size + 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** Prints the items in the deque from first to last,
     * separated by a space.
     * Once all the items have been printed, print out a new line. */
    public void printDeque() {
        int i = plusOne(nextFirst);
        while (items[i] != null){
            System.out.print(items[i] + " ");
            i = plusOne(i);
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * ONE ISSUE I HAVE IS IN THIS FUNCITON AND THE NEXT
     * IF TEMP IS NOT A PRIMITIVE AND I NULL IT OUT, I LOSE IT.*/
    public Zaphod removeFirst(){
        int first = plusOne(nextFirst);
        Zaphod temp = items[first];
        items[first] = null;
        nextFirst = plusOne(nextFirst);
        size = size - 1;
        if (size / items.length < 0.25){
            resize(items.length / RFACTOR);
        }
        return temp;
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null. */
    public Zaphod removeLast() {
        int last = minusOne(nextLast);
        Zaphod temp = items[last];
        items[last] = null;
        nextLast = minusOne(nextLast);
        size = size - 1;
        if (size / items.length < 0.25){
            resize(items.length / RFACTOR);
        }
        return temp;
    }
    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!*/
    public Zaphod get(int index) {
        if (index >= size || index >= items.length){
            return null;
        }
        int start = plusOne(nextFirst);
        if (start + index < items.length){
            return items[start + index];
        } else {
            int firstPart = items.length - start;
            return items[index - firstPart];
        }
    }

    /** Returns the index immediately before the given index considering the circular array format */
    public int minusOne(int index){
        if (index - 1 < 0){
            return items.length - 1;
        } else {
            return index - 1;
        }
    }

    /** Returns the index immediately after the given index considering the circular array format */
    public int plusOne(int index){
        if (index + 1 > items.length - 1){
            return 0;
        } else {
            return index + 1;
        }
    }

    /** Creates a deep copy of other */
    public ArrayDeque(ArrayDeque other){
        items = (Zaphod[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        RFACTOR = 2;
        for(int i = 0; i < other.size(); i++){
            addLast((Zaphod) other.get(i));
        }
    }
}
