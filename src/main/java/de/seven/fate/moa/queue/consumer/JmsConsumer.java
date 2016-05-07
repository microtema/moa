package de.seven.fate.moa.queue.consumer;

/**
 * Created by Mario on 07.05.2016.
 */
public interface JmsConsumer<T> {

    void process(T messageObject);

    String getObjectType();
}
