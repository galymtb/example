package com.example.thread.dispatcher;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import com.example.thread.message.Message;
import com.example.thread.queue.SpscRingBuffer;
import com.google.inject.Singleton;

/**
 * Single-threaded processing and dispatching
 */
@Singleton
public class SimpleSubscriptionDispatcher extends BaseSubscriptionDispatcher {

    private final SpscRingBuffer<Message> _queue = new SpscRingBuffer<>(Integer.MAX_VALUE / 2);
    private final ConcurrentMap<Integer, CopyOnWriteArrayList<Consumer<Message>>> _subscribers = new ConcurrentHashMap<>();
    private final Thread _worker = new Thread(this::processMessage);

    @Override
    public void schedule(Message message) {
        _queue.offer(message);
    }

    @Override
    public <T extends Message> void subscribe(int msgType, Consumer<T> process) {
        CopyOnWriteArrayList<Consumer<Message>> list = _subscribers.computeIfAbsent(msgType, k -> new CopyOnWriteArrayList<>());
        Consumer<Message> wrapper = (Message m) -> process.accept((T) m);
        list.add(wrapper);
    }

    @Override
    public void start() {
        _worker.setDaemon(true);
        _worker.start();
    }

    private void processMessage() {
        while (!Thread.currentThread().isInterrupted()) {
            Message msg = _queue.poll();
            if (msg == null) continue;
            List<Consumer<Message>> list = _subscribers.get(msg.getMsgType());
            if (list != null && !list.isEmpty()) {
                for (Consumer<Message> consumer : list) {
                    consumer.accept(msg);
                }
            }
        }
    }

}
