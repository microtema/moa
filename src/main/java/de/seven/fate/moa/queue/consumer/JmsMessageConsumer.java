package de.seven.fate.moa.queue.consumer;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/ExpiryQueue")
})
@ApplicationScoped
public class JmsMessageConsumer implements MessageListener {

    @Inject
    private Logger logger;

    @Override
    public void onMessage(Message message) {

        try {

            logger.info("process JMSMessage: " + message.getJMSMessageID());

            if (message instanceof TextMessage) {

                TextMessage textMessage = (TextMessage) message;

                String messageType = textMessage.getStringProperty("type");

                String text = textMessage.getText();

                logger.info("process Message from queue: " + messageType + " with message: " + text);

            } else {
                logger.warning("Unsupported Message type: " + message.getClass());
            }

        } catch (JMSException e) {
            logger.log(Level.WARNING, "unable to process jms message" + message, e);
        }
    }

}