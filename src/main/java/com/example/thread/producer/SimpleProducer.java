package com.example.thread.producer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.example.thread.Bootable;
import com.example.thread.dispatcher.MessageDispatcher;
import com.example.thread.message.Message;
import com.example.thread.message.SomeMessage;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class SimpleProducer implements Bootable, Producer {

    private final String _fileName;
    private final int _poisonPill;
    private final MessageDispatcher _dispatcher;

    @Inject
    public SimpleProducer(@Named("filePath") String fileName,
                          @Named("poisonPill") int poisonPill,
                          MessageDispatcher dispatcher) {
        _fileName = fileName;
        _poisonPill = poisonPill;
        _dispatcher = dispatcher;
    }

    @Override
    public void start() {
        // move file data reader as a separate interface & call some method to get next line, in tests do not use real file reader
        try (BufferedReader br = new BufferedReader(new FileReader(_fileName))) {
            String line;
            while((line = br.readLine()) != null) {
                Message msg = createMessage(line);
                _dispatcher.schedule(msg);
            }
            Message msg = createMessage(String.valueOf(_poisonPill));
            _dispatcher.schedule(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SomeMessage createMessage(String data) {
        SomeMessage msg = new SomeMessage();
        msg.setData(data);
        return msg;
    }

}
