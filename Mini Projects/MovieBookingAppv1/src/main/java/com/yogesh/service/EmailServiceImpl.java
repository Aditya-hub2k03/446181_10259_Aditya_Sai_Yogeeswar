package com.yogesh.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    @Async
    public void sendEmail(String message) {
        // Placeholder for actual email sending logic
        System.out.println("Sending email with message: " + message);

        // TODO: Integrate actual email sending logic using JavaMailSender or any other provider
    }
}
