package com.example.thread.dispatcher;

import java.util.function.Consumer;

import com.example.thread.message.Message;
import com.google.inject.Singleton;

/**
 * Single-threaded processing and dispatching
 */
@Singleton
public class SimpleSubscriptionDispatcher extends BaseSubscriptionDispatcher {

    @Override
    public void schedule(Message message) {}

    @Override
    public <T extends Message> void subscribe(int msgType, Consumer<T> process) {
    }

    @Override
    public void start() {
    }

}
