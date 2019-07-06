package es.datastructur.synthesizer;

import java.lang.reflect.Array;
import java.util.Iterator;


public class ArrayRingBuffer<T> implements BoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    /* Value for capacity. - Sam */
    private int capacity;

    /** Implements BoundedQueue capacity method. */
    @Override
    public int capacity() {
        return capacity;
    }

    /** Implements BoundedQueue fillCount method */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring buff overflow");
        }
        rb[last] = x;
        fillCount += 1;

        if (last + 1 == capacity) {
            last = 0;
        } else {
            last += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        T returnItem = rb[first];
        rb[first] = null;
        fillCount -= 1;

        if (first + 1 == capacity) {
            first = 0;
        } else {
            first += 1;
        }

        return returnItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int wizPos;

        public ArrayRingBufferIterator() {
            wizPos = 0;
        }

        @Override
        public boolean hasNext() {
            return wizPos < fillCount();
        }

        @Override
        public T next() {
            T returnItem = rb[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    public boolean equals(ArrayRingBuffer<T> other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if (o.fillCount() != this.fillCount()) {
            return false;
        }
        if (o.capacity() != this.capacity()) {
            return false;
        }
        for (int i = 0; i < fillCount(); i++) {
            if (o.rb[i] != this.rb[i]) {
                return false;
            }
        }

        return true;
    }
}
