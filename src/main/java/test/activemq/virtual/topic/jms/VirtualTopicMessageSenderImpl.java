/**
 * 
 */
package test.activemq.virtual.topic.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * @author gselvaratnam
 *
 */
@Component
@Profile("JMSSender")
public class VirtualTopicMessageSenderImpl implements VirtualTopicMessageSender {

    private static final Logger logger = LoggerFactory.getLogger(VirtualTopicMessageSenderImpl.class);

    @Autowired
    @Qualifier("payloadBatchJmsTemplate")
    private JmsTemplate         payloadBatchJmsTemplate;

    @Value("${meterdata.activemq.payload.batch.jms.template.delay:10000}")
    private int                 jmsMsgDelay;

    /*
     * (non-Javadoc)
     * 
     * @see test.activemq.virtual.topic.jms.VirtualTopicMessageSender#
     * sendMessageToVirtualTopic(java.lang.String)
     */
    public void sendMessageToVirtualTopic(final String message) {
        logger.debug("BEGIN::VirtualTopicMessageSenderImpl.sendMessageToVirtualTopic");

        payloadBatchJmsTemplate.send(new MessageCreator() {

            public Message createMessage(Session session) throws JMSException {
                Message theMessage = session.createTextMessage(message);
                theMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, jmsMsgDelay);
                return theMessage;
            }
        });

        logger.debug("END::VirtualTopicMessageSenderImpl.sendMessageToVirtualTopic");
    }

}
