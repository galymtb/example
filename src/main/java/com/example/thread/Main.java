package com.example.thread;

import com.example.thread.consumer.Consumer;
import com.example.thread.dispatcher.MessageDispatcher;
import com.example.thread.producer.Producer;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

    private static final Injector _injector = Guice.createInjector(new Module());

    public static void main(String[] args) {

        Producer producer = _injector.getInstance(Producer.class);
        producer.start();

        _injector.getInstance(Consumer.class);

        MessageDispatcher dispatcher = _injector.getInstance(MessageDispatcher.class);
        dispatcher.start();

        // TODO: find logic to stop main thread once worker threads finished
        while(true);

    }

}
