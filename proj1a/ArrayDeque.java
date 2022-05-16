public class ArrayDeque<T> {
    private T[] items;
    private int left;
    private int right;
    private int size;
    private int length;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        left = right = 4;
        size = 0;
        length = 8;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private int minusOne(int index) {
        if (index == 0) {
            return length - 1;
        }
        return index - 1;
    }

    /** something may be wrong here */
    private int plusOne(int index, int module) {
        index %= module;
        if (index == length - 1) {
            return 0;
        }
        return index + 1;
    }

    private void grow() {
        T[] newArray = (T[]) new Object[length * 2];
        int ptr1 = left;
        int ptr2 = length;
        while (ptr1 != right) {
            newArray[ptr2] = items[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length * 2);
        }
    }

    private void shrink() {
        T[] newArray = (T[]) new Object[length / 2];
        int ptr1 = left;
        int ptr2 = length / 4;
        while (ptr1 != right) {
            newArray[ptr2] = items[ptr1];
            ptr1 = plusOne(ptr1, length);
            ptr2 = plusOne(ptr2, length / 2);
        }
        left = ptr2;
        right = length / 4;
        items = newArray;
        length /= 2;
    }

    public void addFirst(T item) {
        if (size == length - 1) {
            grow();
        }
        left = minusOne(left);
        items[left] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == length - 1) {
            grow();
        }
        items[right] = item;
        right = plusOne(right, length);
        size++;
    }

    public T removeFirst() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        T item = items[left];
        left = plusOne(left, length);
        size--;
        return item;
    }

    public T removeLast() {
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        right = minusOne(right);
        T item = items[right];
        size--;
        return item;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = left;
        while (index != 0) {
            ptr = plusOne(ptr, length);
            index--;
        }
        return items[ptr];
    }

    public void printDeque() {
        int ptr = left;
        while (ptr != right) {
            System.out.print(items[ptr] + " ");
            ptr = plusOne(ptr, length);
        }
    }
}
