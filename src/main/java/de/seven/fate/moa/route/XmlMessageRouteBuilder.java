package de.seven.fate.moa.route;

import de.seven.fate.moa.dto.MessagesDTO;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.bind.JAXBContext;

import org.apache.camel.converter.jaxb.JaxbDataFormat;

@Singleton
@Startup
public class XmlMessageRouteBuilder extends RouteBuilder {

    private static final JaxbDataFormat messagesData = new JaxbDataFormat();
    private static final CamelContext camelContext = new DefaultCamelContext();

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


    @PostConstruct
    private void init() {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(MessagesDTO.class);

            messagesData.setContext(jaxbContext);

            camelContext.addRoutes(this);

            camelContext.start();
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
    }

    @Override
    public void configure() {

        //Dead Letter Channel on failed transaction
        errorHandler(deadLetterChannel(dlqUri));

        // in case of any exception then try to redeliver up till 2 times
        onException(Exception.class).maximumRedeliveries(maximumRedeliveries).redeliveryDelay(redeliveryDelay);

        from(fromUri).unmarshal(messagesData).to(toUri);
    }

}