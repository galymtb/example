package com.example.thread.dispatcher;


public class ThreadedMessageDispatcher extends BaseMessageDispatcher {

    private final Thread _worker = new Thread(this::processMessage);

    @Override
    public void start() {
        _worker.setDaemon(true);
        _worker.start();
    }

}
