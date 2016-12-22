/**
 * Copyright © 2005-2016 California Independent System Operator
 * $Revision: #1 $
 * $Date: Mar 11, 2016 $
 * $Author: sgautam $
 */
package test.activemq.virtual.topic;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

/**
 * This configuration defines JMS related settings.
 *
 * @author gselvara
 *
 */
@Configuration
@EnableJms
@Profile("JMSConfigQueueMessageReciever")
public class JMSConfigQueueMessageReciever {

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
    public JmsListenerContainerFactory listenerContainerFactory(@Qualifier(value = "rcintJmsConnectionFactory") ConnectionFactory rcintJmsConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(rcintJmsConnectionFactory);
        factory.setConcurrency(activeNoOfConcurrentMessageListners);
        return factory;
    }
}