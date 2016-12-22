/**
 * Copyright © 2005-2016 California Independent System Operator
 * $Revision: #1 $
 * $Date: Mar 11, 2016 $
 * $Author: sgautam $
 */
package test.activemq.virtual.topic;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.destination.DestinationResolver;

import test.activemq.virtual.topic.jms.TopicMessageRecieverImpl;

/**
 * This configuration defines JMS related settings.
 *
 * @author gselvara
 *
 */
@Configuration
@EnableJms
@Profile("JMSConfigTopicMessageReciever")
public class JMSConfigTopicMessageReciever {

    @Value("${rcint.activemq.url}")
    private String activeMqUrl;

    @Value("${rcint.activemq.max.connections:5}")
    private int    maxJmsConnections;

    @Value("${rcint.activemq.no.of.concurrent.listners:3-5}")
    private String activeNoOfConcurrentMessageListners;

    @Bean(name = "rcintJmsConnectionFactory", destroyMethod = "stop")
    public ConnectionFactory getConnectionFactory() {
        PooledConnectionFactory connectionFactory = new PooledConnectionFactory();
        ActiveMQConnectionFactory acConnectionFactory = new ActiveMQConnectionFactory(activeMqUrl);
        connectionFactory.setConnectionFactory(acConnectionFactory);
        connectionFactory.setMaxConnections(maxJmsConnections);
        connectionFactory.setCreateConnectionOnStartup(false);

        return connectionFactory;
    }

    @Bean
    public DefaultMessageListenerContainer getDefaultMessageListenerContainer(
            @Qualifier(value = "payloadBatchDestination") Topic destination,
            @Qualifier(value = "rcintJmsConnectionFactory") ConnectionFactory rcintJmsConnectionFactory, 
            MessageListener topicMessageReciever) {
        DefaultMessageListenerContainer defMsgListenerContainer = new DefaultMessageListenerContainer();
        defMsgListenerContainer.setDestination(destination);
        defMsgListenerContainer.setConnectionFactory(rcintJmsConnectionFactory);
        defMsgListenerContainer.setPubSubDomain(true);
        defMsgListenerContainer.setDurableSubscriptionName("myVtopic");
        defMsgListenerContainer.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        defMsgListenerContainer.setClientId("consumer_1");
        defMsgListenerContainer.setMessageListener(topicMessageReciever);
        return defMsgListenerContainer;
    }

    @Bean
    public MessageListener getTopicMessageReciever() {
        TopicMessageRecieverImpl topicMessageRecieverImpl = new TopicMessageRecieverImpl();
        return topicMessageRecieverImpl;
    }
//    @Bean
//    public JmsListenerContainerFactory listenerContainerFactory(@Qualifier(value = "rcintJmsConnectionFactory") ConnectionFactory rcintJmsConnectionFactory) {
//        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        factory.setConnectionFactory(rcintJmsConnectionFactory);
//        factory.setConcurrency(activeNoOfConcurrentMessageListners);
//        factory.setPubSubDomain(true);
//        return factory;
//    }

    @Bean(name = "payloadBatchDestination")
    public Topic getActiveMQTopic() {
        ActiveMQTopic newQueue = new ActiveMQTopic("VirtualTopic.Order");
        return newQueue;
    }

}