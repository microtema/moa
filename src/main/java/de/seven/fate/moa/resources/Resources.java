package de.seven.fate.moa.resources;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * This class uses CDI to alias Java EE resources, such as the Logger, to CDI beans
 */
public class Resources {

    public static final String QUEUE_NAME = "java:/jms/queue/ExpiryQueue";
    public static final String JMS_CONNECTION_FACTORY = "java:jboss/DefaultJMSConnectionFactory";

    /**
     *  put this in your class
     *  @Inject
     *  private Logger logger;
     *
     * @param injectionPoint
     * @return Logger
     */
    @Produces
    @Named("logger")
    public Logger produceLog(InjectionPoint injectionPoint) {

        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

}