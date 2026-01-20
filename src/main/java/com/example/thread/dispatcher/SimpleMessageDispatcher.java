package com.example.thread.dispatcher;

import com.google.inject.Singleton;

@Singleton
public class SimpleMessageDispatcher extends BaseMessageDispatcher {

    @Override
    public void start() {
        this.processMessage();
    }

}
