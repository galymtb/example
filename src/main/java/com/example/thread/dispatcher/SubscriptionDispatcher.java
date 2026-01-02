package com.example.thread.dispatcher;

import java.util.function.Consumer;

import com.example.thread.Bootable;
import com.example.thread.message.Message;

public interface SubscriptionDispatcher extends MessageDispatcher, Bootable {

    <T extends Message> void subscribe(int msgType, Consumer<T> process);

}
