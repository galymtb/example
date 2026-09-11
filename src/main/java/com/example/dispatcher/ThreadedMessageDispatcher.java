package com.example.dispatcher;

public class ThreadedMessageDispatcher extends BaseMessageDispatcher {

    private final Thread _worker = new Thread(this::dispatchAll);

    @Override
    public void start() {
        _worker.setDaemon(true);
        _worker.start();
        try {
            _worker.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
