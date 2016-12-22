/**
 * 
 */
package test.activemq.virtual.topic.jms;

/**
 * @author gselvaratnam
 *
 */
public interface TopicMessageReciever {

	void recieveMessageFromTopic(String message);
}
