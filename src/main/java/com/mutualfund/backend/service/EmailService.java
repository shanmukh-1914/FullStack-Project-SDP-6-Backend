package com.mutualfund.backend.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

//Service layer handling business logic for User operations
// Connects controller with repository layer
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendWelcomeEmail(String to, String fullName) {
        String subject = "Welcome to MutualFund Pro!";
        String body = "Dear " + fullName + ",\n\n"
                + "Welcome to MutualFund Pro - FSAD-PS06.\n"
                + "Your account has been created successfully.\n\n"
                + "Start exploring mutual funds today!\n\n"
                + "Regards,\nMutualFund Pro Team";
        sendEmail(to, subject, body);
    }

    public void sendQueryReplyEmail(String to, String investorName, String replyText) {
        String subject = "Your query has been answered - MutualFund Pro";
        String body = "Dear " + investorName + ",\n\n"
                + "Your query has been answered by our financial advisor:\n\n"
                + replyText + "\n\n"
                + "Regards,\nMutualFund Pro Team";
        sendEmail(to, subject, body);
    }
}