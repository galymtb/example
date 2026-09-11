package com.example.queue;

public class RingBuffer<E> {

    private final int capacity;
    private final int mask;
    public E[] elements;

    private int count = 0;
    private int writePos  = 0;
    private int readPos = 0;

    public RingBuffer(int capacity) {
        int cap = 1;
        while (cap < capacity) {
            cap <<= 1;
        }
        this.capacity = cap;
        this.mask = cap - 1;
        this.elements = (E[]) new Object[cap];
    }

    public boolean put(E element){
        if(count == capacity) return false;
        elements[writePos] = element;
        writePos = (writePos + 1) & mask;
        count++;
        return true;
    }

    public E take() {
        if(count == 0) return null;
        E element = elements[readPos];
        elements[readPos] = null;
        readPos = (readPos + 1) & mask;
        count--;
        return element;
    }

}
