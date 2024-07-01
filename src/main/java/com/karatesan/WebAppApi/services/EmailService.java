package com.karatesan.WebAppApi.services;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void test(){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("maciej.gomulec@gmail.com");
        message.setSubject("test");
        message.setText("Test");
        mailSender.send(message);

    }
//TODO ogarnac exception MessagingException
    public void sendEmailFromTemplate() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("sender@example.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, "maciej.gomulec@gmail.com");
        message.setSubject("Test email from my Springapplication");

        // Read the HTML template into a String variable
        String htmlTemplate = "<h1>This is a test Spring Boot email to ${name}</h1>" +
                "<p>It can contain <strong>HTML</strong> content.<br/> And the message is ${message}</p>";

        // Replace placeholders in the HTML template with dynamic values
        htmlTemplate = htmlTemplate.replace("${name}", "John Doe");
        htmlTemplate = htmlTemplate.replace("${message}", "Hello, this is a test email.");

        // Set the email's content to be the HTML template
        message.setContent(htmlTemplate, "text/html; charset=utf-8");

        mailSender.send(message);
    }
}
