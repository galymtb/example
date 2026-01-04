package com.example.thread.dispatcher;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import com.example.thread.message.Message;
import com.google.inject.Singleton;

/**
 * Multithreaded dispatching
 */
@Singleton
public class ThreadedSubscriptionDispatcher extends BaseSubscriptionDispatcher {

    private final BlockingQueue<Message> _queue = new LinkedBlockingQueue<>();
    private final ConcurrentMap<Integer, CopyOnWriteArrayList<Consumer<Message>>> _subscribers = new ConcurrentHashMap<>();
    private final ExecutorService _executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Override
    public void schedule(Message message) {
        _queue.offer(message);
        _executorService.submit(this::processMessage);
    }

    @Override
    public <T extends Message> void subscribe(int msgType, Consumer<T> process) {
        CopyOnWriteArrayList<Consumer<Message>> list = _subscribers.computeIfAbsent(msgType, k -> new CopyOnWriteArrayList<>());
        Consumer<Message> wrapper = (Message m) -> process.accept((T) m);
        list.add(wrapper);
    }

    @Override
    public void start() {
    }

    private void processMessage() {
        try {
            Message msg = _queue.take();
            List<Consumer<Message>> list = _subscribers.get(msg.getMsgType());
            if (list != null && !list.isEmpty()) {
                for (Consumer<Message> consumer : list) {
                    consumer.accept(msg);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
