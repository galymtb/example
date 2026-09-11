package com.example;

import com.example.consumer.ConsumerImpl;
import com.example.dispatcher.SimpleMessageDispatcher;
import com.example.dispatcher.MessageDispatcher;
import com.example.dispatcher.ThreadedMessageDispatcher;
import com.example.producer.ProducerImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

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
    public MessageDispatcher getSubscriptionDispatcher() {
        return new ThreadedMessageDispatcher();
    }

    @Provides
    @Singleton
    public ProducerImpl getProducer(MessageDispatcher dispatcher, String filePath, int poisonPill) {
        return new ProducerImpl(dispatcher, filePath, poisonPill);
    }

    @Provides
    @Singleton
    public ConsumerImpl getConsumer(MessageDispatcher dispatcher, int poisonPill) {
        return new ConsumerImpl(dispatcher, poisonPill);
    }

}
