package de.seven.fate.moa.route;

import de.seven.fate.moa.dto.MessagesDTO;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;

import org.apache.camel.converter.jaxb.JaxbDataFormat;

@Singleton
@Startup
public class XmlMessageRouteBuilder extends RouteBuilder {

    private final JaxbDataFormat messagesData = new JaxbDataFormat();
    private final CamelContext camelContext = new DefaultCamelContext();

    @Resource(lookup = "java:global/IMPORT_PATH")
    private String uri;

    @Inject
    private XmlMessageProcessor messageProcessor;


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

        from(uri)
                .unmarshal(messagesData)
                // .to("jms:queue:ExpiryQueue?connectionFactory=ConnectionFactory");
                .process(messageProcessor);
    }

}