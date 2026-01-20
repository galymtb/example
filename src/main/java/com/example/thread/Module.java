package com.example.thread;

import com.example.thread.consumer.Consumer;
import com.example.thread.consumer.SimpleConsumer;
import com.example.thread.dispatcher.SimpleMessageDispatcher;
import com.example.thread.dispatcher.MessageDispatcher;
import com.example.thread.dispatcher.ThreadedMessageDispatcher;
import com.example.thread.producer.Producer;
import com.example.thread.producer.SimpleProducer;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

public class Module extends AbstractModule {

    @Provides
    @Singleton
    String provideFilePath() {
        return "src/main/resources/SCH.log";
    }

    @Provides
    @Singleton
    int providePoisonPill() {
        return Integer.MAX_VALUE;
    }

    @Provides
    @Singleton
    @Inject
    public MessageDispatcher getSubscriptionDispatcher() {
        return new ThreadedMessageDispatcher();
    }

    @Provides
    @Singleton
    @Inject
    public Producer getProducer(String filePath, int poisonPill, MessageDispatcher dispatcher) {
        return new SimpleProducer(filePath, poisonPill, dispatcher);
    }

    @Provides
    @Singleton
    @Inject
    public Consumer getConsumer(int poisonPill, MessageDispatcher dispatcher) {
        return new SimpleConsumer(poisonPill, dispatcher);
    }

}
