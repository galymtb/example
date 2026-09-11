package com.example.consumer;

import com.example.dispatcher.MessageDispatcher;
import com.example.message.MsgTypes;
import com.example.message.SomeMessage;

public class ConsumerImpl {

    private final int _poisonPill;

    public ConsumerImpl(MessageDispatcher dispatcher, int poisonPill) {
        dispatcher.subscribe(MsgTypes.MESSAGE_TYPE, this::handleMsg);
        _poisonPill = poisonPill;
    }

    private void handleMsg(SomeMessage msg) {
        String data = msg.getData();
        if (data != null && data.equals(String.valueOf(_poisonPill))) {
            return;
        }
        System.out.println(data);
    }

}
