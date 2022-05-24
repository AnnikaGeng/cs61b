package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    private int getNextIndex(int n, int capacity) {
        if (n < capacity - 1) {
            n += 1;
            return n;
        } else {
            return (n + 1) % capacity;
        }
    }

    @Override
    public void enqueue(T x) {
        if (fillCount == 0) {
            rb[first] = x;
            rb[last] = x;
            last++;
            fillCount++;
        } else if (fillCount < capacity) {
            rb[last] = x;
            last = getNextIndex(last, capacity);
            fillCount++;
        } else {
            throw new RuntimeException("overflow");
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {

        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T res = rb[first];
            first = getNextIndex(first, capacity);
            fillCount--;
            return res;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {

        if (fillCount == 0) {
            return null;
        } else {
            return rb[first];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }


    private class BufferIterator implements Iterator<T> {
        private int position;
        private int curNum;

        BufferIterator() {
            position = first;
            curNum = 0;
        }

        public boolean hasNext() {
            return curNum < fillCount;
        }

        public T next() {
            T returnVal = (T) rb[position];
            position = (position + 1) % capacity;
            curNum++;
            return returnVal;
        }
    }
}
