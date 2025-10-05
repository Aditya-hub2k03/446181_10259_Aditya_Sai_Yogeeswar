package com.yogesh.kafka;

import com.yogesh.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "email_topic", groupId = "email_group")
    public void consumeEmailRequest(String emailMessage) {
        // Process the message, e.g., send email asynchronously
        emailService.sendEmail(emailMessage);
    }
}
