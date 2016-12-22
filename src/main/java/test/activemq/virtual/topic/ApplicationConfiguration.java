package test.activemq.virtual.topic;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages={"test.activemq.virtual.topic.*"})
@Import({ JMSConfigSender.class})
public class ApplicationConfiguration {

}	
