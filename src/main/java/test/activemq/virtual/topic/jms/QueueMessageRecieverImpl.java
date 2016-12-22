/**
 * 
 */
package test.activemq.virtual.topic.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author gselvaratnam
 *
 */
@Component
@Profile("JMSConfigQueueMessageReciever")
public class QueueMessageRecieverImpl implements QueueMessageReciever {

    private static final Logger logger = LoggerFactory.getLogger(QueueMessageRecieverImpl.class);

    public QueueMessageRecieverImpl() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see test.activemq.virtual.topic.jms.QueueMessageReciever#
     * recieveMessageFromQueue(java.lang.String)
     */
    @JmsListener(destination = "Consumer.A.VirtualTopic.Order")
    public void recieveMessageFromQueue(String message) {
        logger.debug("BEGIN::QueueMessageRecieverImpl.recieveMessageFromQueue");

        logger.info("Recived a message {}", message);

        logger.debug("END::QueueMessageRecieverImpl.recieveMessageFromQueue");
    }
}
