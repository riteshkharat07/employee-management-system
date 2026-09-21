package com.ritesh.employeeservice.config;

import com.ritesh.employeeservice.event.EmployeeEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, EmployeeEvent> producerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                "bootstrap.servers",
                "kafka:9092"
        );

        config.put(
                "key.serializer",
                StringSerializer.class
        );


        return new DefaultKafkaProducerFactory<>(
                config,
                new StringSerializer(),
                new org.springframework.kafka.support.serializer.JacksonJsonSerializer<>()
             );
    }

    @Bean
    public KafkaTemplate<String, EmployeeEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic employeeEventsTopic() {
        return new NewTopic("employee-events", 3, (short) 1);
    }
    @Bean
    public ConsumerFactory<String, EmployeeEvent> consumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka:9092"
        );

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "employee-consumer-group"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JacksonJsonDeserializer.class
        );

        config.put(
                "spring.json.trusted.packages",
                "com.ritesh.employeeservice.event"
        );

        config.put(
                "spring.json.value.default.type",
                "com.ritesh.employeeservice.event.EmployeeEvent"
        );

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, EmployeeEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, EmployeeEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}