package com.example.thread;

import java.util.concurrent.BlockingQueue;

public class Consumer extends Thread {
    private final BlockingQueue<String> _queue;
    private final int _poisonPill;

    public Consumer(BlockingQueue<String> queue, int poisonPill) {
        this._queue = queue;
        this._poisonPill = poisonPill;
    }

    @Override
    public void run() {
        while (true) {
            String element = _queue.poll();
            if (element != null && element.equals(String.valueOf(_poisonPill))) {
                break;
            }
            System.out.println(element);
        }
    }
}
