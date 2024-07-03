package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.exception.EmailSendingException;
import com.karatesan.WebAppApi.utility.EmaiLConstans;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void test() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("maciej.gomulec@gmail.com");
        message.setSubject("test");
        message.setText("Test");
        mailSender.send(message);

    }

    //TODO ogarnac exception MessagingException
    public void sendVerificationEmail(String firstName, String email, String token) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(EmaiLConstans.ACCOUNT_VERIFICATION_SUBJECT);

            // Read the HTML template into a String variable
            String htmlTemplate = EmaiLConstans.ACCOUNT_VERIFICATION_MESSAGE;

            // Replace placeholders in the HTML template with dynamic values
            htmlTemplate = htmlTemplate.replace("${name}", firstName);
            htmlTemplate = htmlTemplate.replace("${link}", EmaiLConstans.ACTIVATION_ENDPOINT + token);

            // Set the email's content to be the HTML template
            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MessagingException ex) {
            throw new EmailSendingException("Failed to send message to " + email, ex.getCause());
        }

    }
}

