package com.example.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        String filePath = "src/main/resources/SCH.log";
        int poisonPill = Integer.MAX_VALUE;
        BlockingQueue<String> queue = new LinkedBlockingDeque<>();
        Thread producerThread = new Thread(new Producer(filePath, queue, poisonPill));
        Thread consumerThread = new Thread(new Consumer(queue, poisonPill));

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();
    }
}
