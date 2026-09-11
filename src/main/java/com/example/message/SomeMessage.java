package com.example.message;

public class SomeMessage implements Message {

    private String _data;

    public SomeMessage() {}

    public void setData(String data) { _data = data; }

    public String getData() { return _data; }

    @Override
    public int getMsgType() {
        return MsgTypes.MESSAGE_TYPE;
    }

}
