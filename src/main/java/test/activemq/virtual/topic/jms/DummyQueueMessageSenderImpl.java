/**
 * 
 */
package test.activemq.virtual.topic.jms;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author gselvaratnam
 *
 */
@Component
@Profile({"JMSConfigTopicMessageReciever"})
public class DummyQueueMessageSenderImpl implements VirtualTopicMessageSender {

    /* (non-Javadoc)
     * @see test.activemq.virtual.topic.jms.VirtualTopicMessageSender#sendMessageToVirtualTopic(java.lang.String)
     */
    public void sendMessageToVirtualTopic(String message) {
        throw new RuntimeException("This method should not be called.");
    }
}
