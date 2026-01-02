package com.example.thread.dispatcher;

import com.example.thread.message.Message;

public interface MessageDispatcher {

    void schedule(Message message);

}
