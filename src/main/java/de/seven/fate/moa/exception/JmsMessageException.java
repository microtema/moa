package de.seven.fate.moa.exception;

import javax.jms.JMSException;

/**
 * Created by Mario on 11.05.2016.
 */
public class JmsMessageException extends JMSException {

    public JmsMessageException(String message) {
        super(message);
    }
}
