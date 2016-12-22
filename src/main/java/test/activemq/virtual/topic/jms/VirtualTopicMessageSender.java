/**
 * 
 */
package test.activemq.virtual.topic.jms;

/**
 * @author gselvaratnam
 *
 */
public interface VirtualTopicMessageSender {

	void sendMessageToVirtualTopic(String message);
}
