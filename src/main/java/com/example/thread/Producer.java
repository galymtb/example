package com.example.thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Producer extends Thread {
    private final String _fileName;
    private final BlockingQueue<String> _queue;
    private final int _poisonPill;

    public Producer(String fileName, BlockingQueue<String> queue, int poisonPill) {
        this._fileName = fileName;
        this._queue = queue;
        this._poisonPill = poisonPill;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(_fileName))) {
            String line;
            while((line = br.readLine()) != null) {
                _queue.offer(line);
            }
            _queue.offer(String.valueOf(_poisonPill));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
