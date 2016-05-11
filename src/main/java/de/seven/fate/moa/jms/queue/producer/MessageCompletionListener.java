package de.seven.fate.moa.jms.queue.producer;

import javax.inject.Inject;
import javax.jms.CompletionListener;
import javax.jms.Message;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mario on 11.05.2016.
 */
public class MessageCompletionListener implements CompletionListener {

    @Inject
    private Logger logger;

    @Override
    public void onCompletion(Message message) {

        logger.info("send message async: " + message);
    }

    @Override
    public void onException(Message message, Exception exception) {

        logger.log(Level.WARNING, "unable to send message async: " + message, exception);
    }
}
