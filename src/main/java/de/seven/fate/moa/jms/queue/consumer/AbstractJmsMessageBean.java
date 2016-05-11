package de.seven.fate.moa.jms.queue.consumer;

import de.seven.fate.moa.exception.JmsMessageException;
import de.seven.fate.moa.jms.proccesor.JmsProcessor;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class AbstractJmsMessageBean implements MessageListener {

    @Inject
    private Logger logger;


    @Override
    public void onMessage(Message message) {

        try {

            String messageType = message.getStringProperty("type");

            logger.info("process Message with MessageID: " + message.getJMSMessageID() + " and type: " + messageType);

            getJmsProcessor().process(getObject(message));

        } catch (JMSException e) {
            logger.log(Level.WARNING, "unable to process jms message" + message, e);
        }
    }

    protected abstract JmsProcessor getJmsProcessor();

    private Serializable getObject(Message message) throws JMSException {

        if (message instanceof ObjectMessage) {

            ObjectMessage objectMessage = (ObjectMessage) message;

            return objectMessage.getObject();
        }

        throw new JmsMessageException("unsupported message type: " + message.getClass().getSimpleName());
    }

}