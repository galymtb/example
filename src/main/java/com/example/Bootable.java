package com.example;

public interface Bootable {

    default void start() {}

    default void stop() {}

}
