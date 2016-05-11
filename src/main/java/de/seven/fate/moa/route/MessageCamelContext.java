package de.seven.fate.moa.route;

import de.seven.fate.moa.dto.MessagesDTO;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.spi.DataFormat;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Mario on 11.05.2016.
 */
public class MessageCamelContext extends DefaultCamelContext {

    @Inject
    private Logger logger;

    private JaxbDataFormat messagesData;

    @PostConstruct
    private void init() {

        messagesData = new JaxbDataFormat();

        JAXBContext jaxbContext = getJAXBContext();

        messagesData.setContext(jaxbContext);
    }

    private JAXBContext getJAXBContext() {

        try {
            return JAXBContext.newInstance(MessagesDTO.class);
        } catch (JAXBException e) {

            logger.log(Level.WARNING, "unable to create new Instance", e);

            throw new IllegalArgumentException("unable to to create new Instance of JAXBContext.", e);
        }
    }

    public DataFormat getMessagesData() {
        return messagesData;
    }
}
