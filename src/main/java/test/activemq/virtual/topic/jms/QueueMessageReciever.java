/**
 * 
 */
package test.activemq.virtual.topic.jms;

/**
 * @author gselvaratnam
 *
 */
public interface QueueMessageReciever {

	void recieveMessageFromQueue(String message);
}
