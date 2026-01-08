package com.example.thread.dispatcher;

import com.google.inject.Singleton;

@Singleton
public class ThreadedSubscriptionDispatcher extends BaseSubscriptionDispatcher {

    private final Thread _worker = new Thread(this::processMessage);

    @Override
    public void start() {
        _worker.setDaemon(true);
        _worker.start();
    }

}
