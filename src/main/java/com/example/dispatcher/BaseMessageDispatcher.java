package com.example.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import com.example.message.Message;

public abstract class BaseMessageDispatcher implements MessageDispatcher {

    // TODO: implement common logic for subscription dispatchers
    protected final BlockingQueue<Message> _queue = new LinkedBlockingQueue<>();
    protected final HashMap<Integer, List<Consumer<? extends Message>>> _subscribers = new HashMap<>();

    @Override
    public void schedule(Message message) {
        _queue.offer(message);
    }

    @Override
    public <T extends Message> void subscribe(int msgType, Consumer<T> process) {
        // Integer vs int autoboxing: performance related
        _subscribers.computeIfAbsent(msgType, k -> new ArrayList<>()).add(process);
    }

    boolean dispatchNext() {
        Message msg = _queue.poll();
        if (msg == null) return false;

        List<Consumer<? extends Message>> targets  = _subscribers.get(msg.getMsgType());
        if (targets == null || targets.isEmpty()) return true;

        for (Consumer<? extends Message> raw : targets) {
            Consumer<Message> consumer = (Consumer<Message>) raw;
            consumer.accept(msg);
        }
        return true;
    }

    void dispatchAll() {
        while (dispatchNext()) {
        }
    }

}
