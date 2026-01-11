package com.example.thread.dispatcher;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import com.example.thread.message.Message;

public abstract class BaseSubscriptionDispatcher implements SubscriptionDispatcher {

    // TODO: implement common logic for subscription dispatchers
    protected final BlockingQueue<Message> _queue = new LinkedBlockingQueue<>();
    protected final HashMap<Integer, Consumer<Message>> _subscribers = new HashMap<>();

    @Override
    public void schedule(Message message) {
        _queue.offer(message);
    }

    @Override
    public <T extends Message> void subscribe(int msgType, Consumer<T> process) {
        if(_subscribers.containsKey(msgType)) {
            throw new IllegalStateException("1 Subscriber to 1 MsgType");
        }
        // Integer vs int autoboxing: performance related
        _subscribers.put(msgType, (Message m) -> process.accept((T) m));
    }

    void processMessage() {
        while (!Thread.currentThread().isInterrupted()) {
            Message msg = _queue.poll();
            if (msg == null) continue;
            Consumer<Message> consumer = _subscribers.get(msg.getMsgType());
            consumer.accept(msg);
        }
    }

}
