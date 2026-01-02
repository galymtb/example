package com.example.thread.consumer;

import com.example.thread.dispatcher.SubscriptionDispatcher;
import com.example.thread.message.MsgTypes;
import com.example.thread.message.SomeMessage;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class SimpleConsumer implements Consumer {

    private final int _poisonPill;

    @Inject
    public SimpleConsumer(@Named("poisonPill") int poisonPill,
                          SubscriptionDispatcher dispatcher) {
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
