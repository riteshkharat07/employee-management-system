package com.ritesh.notificationservice.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {

    @KafkaListener(
            topics = "employee-events",
            groupId = "notification-consumer-group"
    )
    public void consumeEmployeeEvent(EmployeeEvent event) {

        System.out.println(
                "Notification Service received employee event: " + event
        );
    }
}