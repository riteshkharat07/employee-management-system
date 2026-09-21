package com.ritesh.employeeservice.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProducer {

    private final KafkaTemplate<String, EmployeeEvent> kafkaTemplate;

    public EmployeeProducer(KafkaTemplate<String, EmployeeEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmployeeEvent(EmployeeEvent event) {

        kafkaTemplate.send("employee-events", event);

        System.out.println("Employee event sent to Kafka: " + event);
    }
}