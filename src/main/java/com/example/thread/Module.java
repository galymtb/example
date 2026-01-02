package com.example.thread;

import com.example.thread.consumer.Consumer;
import com.example.thread.consumer.SimpleConsumer;
import com.example.thread.dispatcher.MessageDispatcher;
import com.example.thread.dispatcher.SubscriptionDispatcher;
import com.example.thread.dispatcher.ThreadedSubscriptionDispatcher;
import com.example.thread.producer.Producer;
import com.example.thread.producer.SimpleProducer;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessageDispatcher.class).to(SubscriptionDispatcher.class);
//        bind(SubscriptionDispatcher.class).to(SimpleSubscriptionDispatcher.class);
        bind(SubscriptionDispatcher.class).to(ThreadedSubscriptionDispatcher.class);
        bind(Producer.class).to(SimpleProducer.class);
        bind(Consumer.class).to(SimpleConsumer.class);
    }

    @Provides
    @Named("filePath")
    String provideFilePath() {
        return "src/main/resources/SCS.log";
    }

    @Provides
    @Named("poisonPill")
    Integer providePoisonPill() {
        return Integer.MAX_VALUE;
    }
}
