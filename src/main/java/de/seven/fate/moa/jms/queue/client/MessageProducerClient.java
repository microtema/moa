package de.seven.fate.moa.jms.queue.client;

import de.seven.fate.moa.builder.MessageDTOBuilder;
import de.seven.fate.moa.jms.queue.producer.MessageProducer;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * Created by Mario on 11.05.2016.
 */
@Singleton
public class MessageProducerClient {

    @Inject
    private MessageProducer messageProducer;

    @Inject
    private MessageDTOBuilder builder;

    @Schedule(second = "0/30", minute = "*", hour = "*", persistent = false)
    private void sendMessage() {

        messageProducer.sendMessage(builder.min());
    }
}
