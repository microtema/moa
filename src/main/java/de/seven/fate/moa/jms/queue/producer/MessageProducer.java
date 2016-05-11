package de.seven.fate.moa.jms.queue.producer;

import org.apache.commons.lang3.Validate;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mario on 07.05.2016.
 */
@Stateless
public class MessageProducer {

    @Inject
    private Logger logger;

    @Resource(mappedName = "java:/jms/queue/ExpiryQueue")
    private Queue queue;

    @Inject
    @JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
    private JMSContext context;


    public void sendMessage(Serializable objectModel) {
        Validate.notNull(objectModel);

        Message message = context.createObjectMessage(objectModel);

        applyMessageProperty(message, "type", objectModel.getClass().getSimpleName());

        context.createProducer().send(queue, message);
    }


    private void applyMessageProperty(Message textMessage, String propertyName, String propertyValue) {

        try {
            textMessage.setStringProperty(propertyName, propertyValue);
        } catch (JMSException e) {

            String message = "unable to set property: " + propertyName + " with value: " + propertyName;

            logger.log(Level.WARNING, message, e);

            throw new IllegalArgumentException(message);
        }
    }
}
