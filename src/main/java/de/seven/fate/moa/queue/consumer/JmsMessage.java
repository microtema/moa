package de.seven.fate.moa.queue.consumer;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/ExpiryQueue")
})
@ApplicationScoped
public class JmsMessage implements MessageListener {

    @Inject
    private Logger logger;

    @Inject
    private MessageJmsConsumer consumers;

    @Override
    public void onMessage(Message message) {

        try {

            logger.info("process JMSMessage: " + message.getJMSMessageID());

            String messageType = message.getStringProperty("type");

            JmsConsumer consumer = getMessageConsumer(messageType);

            consumer.process(getObject(message));

        } catch (JMSException e) {
            logger.log(Level.WARNING, "unable to process jms message" + message, e);
        }
    }

    private Serializable getObject(Message message) throws JMSException {

        if (message instanceof ObjectMessage) {

            ObjectMessage objectMessage = (ObjectMessage) message;

            return objectMessage.getObject();
        }

        throw new IllegalArgumentException("unsupported message type: " + message.getClass().getSimpleName());
    }

    private JmsConsumer getMessageConsumer(String messageType) {

        return consumers;
    }

}