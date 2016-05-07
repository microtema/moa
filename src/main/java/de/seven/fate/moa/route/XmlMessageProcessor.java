package de.seven.fate.moa.route;

import de.seven.fate.moa.dto.MessagesDTO;
import de.seven.fate.moa.queue.producer.MessageProducer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import javax.inject.Inject;

public class XmlMessageProcessor implements Processor {

    @Inject
    private MessageProducer messageProducer;

    public void process(Exchange exchange) throws Exception {

        MessagesDTO messagesDTO = exchange.getIn().getBody(MessagesDTO.class);

        messageProducer.sendMessage(messagesDTO);
    }
}