package de.seven.fate.moa.route;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.ValueBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Startup
public class XmlMessageRouteBuilder extends RouteBuilder {


    @Inject
    private Logger logger;

    @Resource(lookup = "java:global/REDELIVERY_DELAY")
    private int redeliveryDelay;

    @Resource(lookup = "java:global/MAXIMUM_REDELIVERIES")
    private int maximumRedeliveries;

    @Resource(lookup = "java:global/IMPORT_URI")
    private String fromUri;

    @Resource(lookup = "java:global/EXPORT_URI")
    private String toUri;

    @Resource(lookup = "java:global/DLQ_URI")
    private String dlqUri;

    @Inject
    private MessageCamelContext messageCamelContext;


    @PostConstruct
    private void init() {

        addRouteToCamelContext();

        startCamelContext();
    }


    @Override
    public void configure() {

        //Dead Letter Channel on failed transaction
        errorHandler(deadLetterChannel(dlqUri));

        // in case of any exception then try to redeliver up till n times with n delay
        onException(Exception.class).maximumRedeliveries(maximumRedeliveries).redeliveryDelay(redeliveryDelay);

        from(fromUri).unmarshal(messageCamelContext.getMessagesData()).to(toUri);
    }


    private void startCamelContext() {

        try {
            messageCamelContext.start();

            logger.info("Camel context started...");
        } catch (Exception e) {
            logger.log(Level.WARNING, "unable to start Camel context", e);
        }
    }

    private void addRouteToCamelContext() {

        try {
            messageCamelContext.addRoutes(this);

        } catch (Exception e) {
            logger.log(Level.WARNING, "unable to add routes", e);
        }
    }

    @PreDestroy
    private void destroy() {

        try {
            messageCamelContext.stop();
            logger.info("Camel context stopped...");
        } catch (Exception e) {
            logger.log(Level.WARNING, "unable to stop messageCamelContext", e);
        }
    }

}