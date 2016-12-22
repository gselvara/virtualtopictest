package test.activemq.virtual.topic;

import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import test.activemq.virtual.topic.jms.VirtualTopicMessageSender;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static final String ARG_JMS_SEND = "send";
    public static final String ARG_JMS_MSG_COUNT = "msgcount=";
    public static final String ARG_JMS_QUEUE_RECIEVE = "reciveQueue";
    public static final String ARG_JMS_TOPIC_RECIEVE = "reciveTopic";

    @Autowired
    private VirtualTopicMessageSender messageSender;

	public static void main(String[] args) {
	    logger.info("BEGIN::Application.main");

	    TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        logger.info("JVM user time zone is defined: {}", TimeZone.getDefault().getID());

        SpringApplication.run(Application.class, args);

        logger.info("END::Application.main");
    }

    /* (non-Javadoc)
     * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
     */
    public void run(String... arg0) throws Exception {
        logger.info("BEGIN::Application.run");

        boolean isSendMessage = false;
        boolean isReciveMessage = false;
        int msgCount = 0;

        for(String newArg : arg0) {
            logger.info("newArg : {}", newArg);
            if(ARG_JMS_SEND.equalsIgnoreCase(newArg)) {
                isSendMessage = true;
                msgCount = Integer.valueOf(getArgContent(arg0, ARG_JMS_MSG_COUNT));
                break;
            }
        }

        logger.info("isSendMessage : {}", isSendMessage);
        
        if(isSendMessage) {
            for(int i=1; i <=msgCount; i++) {
                logger.info("Sending Msg Mo : {}" , i);
                Date theDate = new Date(); 
                messageSender.sendMessageToVirtualTopic("Hello Msg..." + i + " at : " + theDate);

                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
            }
        }

        logger.info("isReciveMessage : {}", isReciveMessage);
//        if(isReciveMessage) {
//            while(true) {
//                
//            }
//        }
        logger.info("END::Application.run");
    }

    private String getArgContent(String [] args, String argName) {
        String responseValue = "";
        for(String newArg : args) {
            if(newArg.contains(argName)) {
                responseValue = newArg.substring(newArg.indexOf(argName) + argName.length());
            }
        }
        return responseValue;
    }
}