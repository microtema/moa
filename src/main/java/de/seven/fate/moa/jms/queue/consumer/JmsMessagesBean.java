package de.seven.fate.moa.jms.queue.consumer;

import de.seven.fate.moa.jms.proccesor.JmsMessagesProcessor;
import de.seven.fate.moa.jms.proccesor.JmsProcessor;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/ExpiryQueue"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "type IS NULL")
}, description = "Handle JMS Messages filtered by MessageSelector [type IS NULL]")
@ApplicationScoped
public class JmsMessagesBean extends AbstractJmsMessageBean {

    @Inject
    private JmsMessagesProcessor processor;


    @Override
    protected JmsProcessor getJmsProcessor() {

        return processor;
    }

}