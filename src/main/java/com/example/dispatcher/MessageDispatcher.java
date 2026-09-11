package com.example.dispatcher;

import java.util.function.Consumer;

import com.example.Bootable;
import com.example.message.Message;

public interface MessageDispatcher extends Bootable {

    void schedule(Message message);

    <T extends Message> void subscribe(int msgType, Consumer<T> process);

}
