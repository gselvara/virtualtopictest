/**
 * Copyright © 2005-2016 California Independent System Operator
 * $Revision: #1 $
 * $Date: Mar 11, 2016 $
 * $Author: sgautam $
 */
package test.activemq.virtual.topic;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
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
import org.springframework.jms.core.JmsTemplate;

/**
 * This configuration defines JMS related settings.
 *
 * @author gselvara
 *
 */
@Configuration
@EnableJms
@Profile("JMSSender")
public class JMSConfigSender {

    @Value("${rcint.activemq.url}")
    private String activeMqUrl;

    @Value("${rcint.activemq.max.connections:5}")
    private int    maxJmsConnections;

    @Bean(name = "stlmtapiConnectionFactory", destroyMethod = "stop")
    public ConnectionFactory getConnectionFactory() {
        PooledConnectionFactory connectionFactory = new PooledConnectionFactory();
        ActiveMQConnectionFactory acConnectionFactory = new ActiveMQConnectionFactory(activeMqUrl);
        connectionFactory.setConnectionFactory(acConnectionFactory);
        connectionFactory.setMaxConnections(maxJmsConnections);
        connectionFactory.setCreateConnectionOnStartup(false);
        return connectionFactory;
    }

    @Bean(name = "payloadBatchJmsTemplate")
    public JmsTemplate getPayloadBatchJmsTemplate(
            @Qualifier(value = "stlmtapiConnectionFactory") ConnectionFactory connectionFactory,
            @Qualifier(value = "payloadBatchDestination") Topic theTopic) {
        JmsTemplate theJmsTemplate = new JmsTemplate();
        theJmsTemplate.setConnectionFactory(connectionFactory);
        theJmsTemplate.setDefaultDestination(theTopic);
        return theJmsTemplate;
    }

    @Bean(name = "payloadBatchDestination")
    public Topic getActiveMQTopic() {
        ActiveMQTopic newQueue = new ActiveMQTopic("VirtualTopic.Order");
        return newQueue;
    }
}