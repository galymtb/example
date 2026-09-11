package com.example.producer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.example.Bootable;
import com.example.dispatcher.MessageDispatcher;
import com.example.message.Message;
import com.example.message.SomeMessage;

public class ProducerImpl implements Bootable {

    private final MessageDispatcher _dispatcher;
    private final String _fileName;
    private final int _poisonPill;

    public ProducerImpl(MessageDispatcher dispatcher, String fileName, int poisonPill) {
        _dispatcher = dispatcher;
        _fileName = fileName;
        _poisonPill = poisonPill;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Message msg = createMessage(String.valueOf(_poisonPill));
            _dispatcher.schedule(msg);
        }
    }

    private SomeMessage createMessage(String data) {
        SomeMessage msg = new SomeMessage();
        msg.setData(data);
        return msg;
    }

}
