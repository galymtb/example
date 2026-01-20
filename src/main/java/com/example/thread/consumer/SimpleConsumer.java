package com.example.thread.consumer;

import com.example.thread.dispatcher.MessageDispatcher;
import com.example.thread.message.MsgTypes;
import com.example.thread.message.SomeMessage;

public class SimpleConsumer implements Consumer {

    private final int _poisonPill;

    public SimpleConsumer(int poisonPill, MessageDispatcher dispatcher) {
        _poisonPill = poisonPill;
        dispatcher.subscribe(MsgTypes.SOME_MESSAGE_TYPE, this::handleMsg);
    }

    private void handleMsg(SomeMessage msg) {
        String data = msg.getData();
        if (data != null && data.equals(String.valueOf(_poisonPill))) {
            return;
        }
        System.out.println(data);
    }

}
