package de.seven.fate.moa.jms.proccesor;

/**
 * Created by Mario on 07.05.2016.
 */
public interface JmsProcessor<T> {

    void process(T messageObject);

    String getObjectType();
}
