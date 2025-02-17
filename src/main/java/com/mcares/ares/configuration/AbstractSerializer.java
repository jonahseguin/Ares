package com.mcares.ares.configuration;


public abstract class AbstractSerializer<T> {

    public AbstractSerializer() {
    }

    public abstract String toString(T data);

    public abstract T fromString(Object data);

}
