package de.seven.fate.moa.queue.producer;

import de.seven.fate.moa.dto.MessagesDTO;
import de.seven.fate.model.util.CollectionUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mario on 07.05.2016.
 */
@Singleton
public class MessageProducer {

    @Inject
    private Logger logger;

    @Resource(mappedName = "java:/jms/queue/ExpiryQueue")
    private Queue queue;

    @Inject
    @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
    private JMSContext context;


    public void sendMessage(Serializable objectModel) {
        logger.info("send Message to queue: " + objectModel);

        Message message = context.createObjectMessage(objectModel);

        applyMessageProperty(message, "type", objectModel.getClass().getSimpleName());

        context.createProducer().send(queue, message);
    }


    private void applyMessageProperty(Message textMessage, String propertyName, String propertyValue) {

        try {
            textMessage.setStringProperty(propertyName, propertyValue);
        } catch (JMSException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new IllegalArgumentException("unable to set property: " + propertyName + " with value: " + propertyName);
        }
    }
}
