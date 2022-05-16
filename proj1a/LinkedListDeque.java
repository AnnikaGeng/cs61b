public class LinkedListDeque<T> {
    private int size;
    private IntNode sentinel;

    public class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;

        public IntNode(T i, LinkedListDeque<T>.IntNode n, LinkedListDeque<T>.IntNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    public LinkedListDeque() {
        sentinel = new IntNode((T) new Object(), null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }


    public void addFirst(T item) {
        size++;
        IntNode cur = new IntNode(item, sentinel.next, sentinel);
        sentinel.next.prev = cur;
        sentinel.next = cur;
    }

    public void addLast(T item) {
        size++;
        IntNode cur = new IntNode(item, sentinel, sentinel.prev);
        sentinel.prev.next = cur;
        sentinel.prev = cur;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (IntNode i = sentinel.next; i != sentinel; i = i.next) {
            if (i.next == sentinel) {
                System.out.println(i.item);
                break;
            }
            System.out.println(i.item + " ");
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T res = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return res;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T res = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return res;
    }

    public T get(int index) {
        if (index > size) {
            return null;
        }
        IntNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index--;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        if (index > size) {
            return null;
        }
        return getRecursive(sentinel.next, index);
    }

    private T getRecursive(LinkedListDeque<T>.IntNode node, int i) {
        if (i == 0) {
            return node.item;
        }
        return getRecursive(node.next, i - 1);
    }
}
