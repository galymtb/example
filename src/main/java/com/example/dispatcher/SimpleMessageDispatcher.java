package com.example.dispatcher;

public class SimpleMessageDispatcher extends BaseMessageDispatcher {

    @Override
    public void start() {
        this.dispatchAll();
    }

}
