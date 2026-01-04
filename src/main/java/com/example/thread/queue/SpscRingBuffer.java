package com.example.thread.queue;

public class SpscRingBuffer<T> implements RingBuffer {
    private final T[] _buffer;
    private final int _mask;
    private volatile int _head = 0;
    private volatile int _tail = 0;

    public SpscRingBuffer(int requestedCapacity) {
        int capacity = nextPowerOfTwo(requestedCapacity);
        _buffer = (T[]) new Object[capacity];
        _mask = capacity - 1;
    }

    private static int nextPowerOfTwo(int x) {
        int n = 1;
        while (n < x) {
            n <<= 1;
            if (n <= 0) {
                n = Integer.MAX_VALUE - 1;
            }
        }
        return n;
    }

    public void offer(T item) {
        int currentTail = _tail;
        int nextTail = (currentTail + 1) & _mask;
        if (nextTail == _head) {
            return;
        }
        _buffer[currentTail] = item;
        _tail = nextTail;
    }

    public T poll() {
        int currentHead = _head;
        if (currentHead == _tail) {
            return null;
        }
        T item = _buffer[currentHead];
        _buffer[currentHead] = null;
        _head = (currentHead + 1) & _mask;
        return item;
    }

    public T take() throws InterruptedException {
        T e;
        while ((e = poll()) == null) {
            Thread.onSpinWait();
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
        }
        return e;
    }

    public T peek() {
        int currentHead = _head;
        if (currentHead == _tail) return null;
        return _buffer[currentHead];
    }

    public int size() {
        int currentHead = _head;
        int currentTail = _tail;
        int s = currentTail - currentHead;
        if (s < 0) s += _buffer.length;
        return s;
    }

    public int capacity() {
        return _buffer.length;
    }

    public boolean isEmpty() {
        return _head == _tail;
    }

    public boolean isFull() {
        return ((_tail + 1) & _mask) == _head;
    }
}