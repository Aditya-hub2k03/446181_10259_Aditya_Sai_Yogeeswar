package com.yogesh.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "email_topic";

    public EmailProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmailRequest(String emailMessage) {
        kafkaTemplate.send(TOPIC, emailMessage);
    }
}
