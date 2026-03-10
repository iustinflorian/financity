package com.gifprojects.financity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${MAIL_USERNAME}")
    private String fromEmail;

    @Async
    public void sendVerificationEmail(String toEmail, String otp) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("One more step!");
            message.setText("You are nearly done. Copy this one time password to get access to your account:\n"
            + otp);

            mailSender.send(message);
            System.out.println("Verification email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send verification email to " + toEmail + ". Error: " + e.getMessage());
        }
    }

    @Async
    public void sendWelcomeEmail(String toEmail, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Welcome to FinanCity! 🚀");
            message.setText("Hi " + username + ",\n\n" +
                    "Your account has been successfully created. " +
                    "Happy saving,\nThe Financity Team");

            mailSender.send(message);
            System.out.println("Welcome email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send welcome email to " + toEmail + ". Error: " + e.getMessage());
        }
    }
}
