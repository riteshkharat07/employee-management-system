package com.ritesh.employeeservice.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmployeeConsumer {

    @KafkaListener(
            topics = "employee-events",
            groupId = "employee-consumer-group"
    )
    public void consumeEmployeeEvent(EmployeeEvent event) {

        System.out.println("Employee event received from Kafka: " + event);
    }
}
