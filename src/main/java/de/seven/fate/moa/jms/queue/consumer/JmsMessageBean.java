package de.seven.fate.moa.jms.queue.consumer;

import de.seven.fate.moa.jms.proccesor.JmsMessageProcessor;
import de.seven.fate.moa.jms.proccesor.JmsProcessor;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/ExpiryQueue"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "type = 'MessageDTO'")
}, description = "Handle only JMS Messages filtered by MessageSelector [type = 'MessageDTO']")
@ApplicationScoped
public class JmsMessageBean extends AbstractJmsMessageBean {


    @Inject
    private JmsMessageProcessor processor;


    @Override
    protected JmsProcessor getJmsProcessor() {

        return processor;
    }
}