package com.example;

import com.example.consumer.ConsumerImpl;
import com.example.dispatcher.MessageDispatcher;
import com.example.producer.ProducerImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

    private static final Injector _injector = Guice.createInjector(new Module());

    public static void main(String[] args) {

        ProducerImpl producer = _injector.getInstance(ProducerImpl.class);
        producer.start();

        _injector.getInstance(ConsumerImpl.class);

        MessageDispatcher dispatcher = _injector.getInstance(MessageDispatcher.class);
        dispatcher.start();
    }

}
