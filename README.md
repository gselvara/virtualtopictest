# virtualtopictest
The is a simple projects that demonstrates how ActiveMQ virtual topics work with Spring Boot.

The only configuration needed on the ActiveMq server XML was 

    <destinations> 
        <queue   physicalName="Consumer.A.VirtualTopic.Order" />
    </destinations>
