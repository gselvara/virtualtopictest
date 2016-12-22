/**
 * 
 */
package test.activemq.virtual.topic.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author gselvaratnam
 *
 */
//@Component
//@Profile("JMSConfigTopicMessageReciever")
public class TopicMessageRecieverImpl implements QueueMessageReciever, MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(TopicMessageRecieverImpl.class);

    public TopicMessageRecieverImpl() {
    }

    /* (non-Javadoc)
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    @Override
    public void onMessage(Message message) {
        logger.debug("BEGIN::QueueMessageRecieverImpl.onMessage");

        TextMessage textMessage = (TextMessage)message;

        try {
            logger.info("Recived a message {}", textMessage.getText());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

        logger.debug("END::QueueMessageRecieverImpl.onMessage");
    }

    /*
     * (non-Javadoc)
     * 
     * @see test.activemq.virtual.topic.jms.QueueMessageReciever#
     * recieveMessageFromQueue(java.lang.String)
     */
//    @JmsListener(id = "consumer1", destination = "VirtualTopic.Order")
    public void recieveMessageFromQueue(String message) {
        logger.debug("BEGIN::QueueMessageRecieverImpl.recieveMessageFromQueue");

        logger.info("Recived a message {}", message);

        logger.debug("END::QueueMessageRecieverImpl.recieveMessageFromQueue");
    }
}
