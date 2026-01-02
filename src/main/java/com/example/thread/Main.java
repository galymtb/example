package com.example.thread;

import com.example.thread.consumer.SimpleConsumer;
import com.example.thread.dispatcher.SubscriptionDispatcher;
import com.example.thread.producer.SimpleProducer;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

    // move to usage of dependency injection (+)
    // execution service usage (+)
    // java OOP & dispatchers (+)
    // junit tests

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Module());

        SubscriptionDispatcher dispatcher = injector.getInstance(SubscriptionDispatcher.class);
        dispatcher.start();

        injector.getInstance(SimpleConsumer.class);

        SimpleProducer producer = injector.getInstance(SimpleProducer.class);
        producer.start();

        while(true){}
    }

}
