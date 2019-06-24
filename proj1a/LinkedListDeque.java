public class LinkedListDeque<Zaphod> {

    public class StuffNode {
        public StuffNode prev;
        public Zaphod item;
        public StuffNode next;

        public StuffNode(StuffNode z, Zaphod i, StuffNode n) {
            item = i;
            next = n;
            prev = z;
        }
    }

    private StuffNode sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new StuffNode(sentinel, null, sentinel);
    }

    public LinkedListDeque(Zaphod x) {
        size = 1;
        sentinel = new StuffNode(null, null, null);
        StuffNode temp = new StuffNode(sentinel, x, sentinel.prev);
        sentinel.next = temp;
        sentinel.prev = temp;
    }

    public void addFirst(Zaphod item) {
        if (size == 0){
            sentinel.next = new StuffNode(sentinel, item, sentinel);
            sentinel.prev = sentinel.next;
        } else {
            sentinel.next.prev = new StuffNode(sentinel, item, sentinel.next);
            sentinel.next = sentinel.next.prev;
        }
        size += 1;
    }

    public void addLast(Zaphod item) {
        if (size == 0){
            sentinel.next = new StuffNode(sentinel, item, sentinel);
            sentinel.prev = sentinel.next;
        } else {
            sentinel.prev.next = new StuffNode(sentinel.prev, item, sentinel);
            sentinel.prev = sentinel.prev.next;
        }
        size += 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        StuffNode p = sentinel;
        while (p.next != sentinel) {
            p = p.next;
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    public Zaphod removeFirst() {
        if (size > 0) {
            size -= 1;
            Zaphod temp = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            return temp;
        }
        return null;
    }

    public Zaphod removeLast() {
        if (size > 0) {
            size -= 1;
            Zaphod temp = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            return temp;
        }
        return null;
    }

    public Zaphod get(int index) {
        StuffNode p = sentinel;
        int x = 0;

        while (p.next != sentinel) {
            p = p.next;
            if (x == index) {
                return p.item;
            }
            x += 1;
        }
        return null;
    }

    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinel = new StuffNode(sentinel, null, sentinel);
        for(int i = 0; i < other.size(); i++){
            addLast((Zaphod) other.get(i));
        }
    }

    public Zaphod getRecHelper(StuffNode s, int index){
        if (s.next == sentinel){
            return null;
        }
        if (index == 0) {
            return s.next.item;
        } else {
            return getRecHelper(s.next, index - 1);
        }
    }

    public Zaphod getRecursive(int index) {
        Zaphod finalItem = getRecHelper(sentinel, index);
        return finalItem;
    }
}
