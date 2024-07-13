package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.exception.EmailSendingException;
import com.karatesan.WebAppApi.ulilityClassess.Token;
import com.karatesan.WebAppApi.utility.EmaiLConstans;
import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    //TODO ogarnac exception MessagingException
    public void sendVerificationEmail(String firstName, String email, String token) {

        try {
            MimeMessage message = createMimeMessage(email);
            String htmlTemplate = createHtmlTemplate(firstName, token);

            // Set the email's content to be the HTML template
            message.setContent(htmlTemplate, "text/html; charset=utf-8");
            mailSender.send(message);
        } catch (MailAuthenticationException ex) {
            //TODO add logger
            // Log the authentication exception with a detailed message
            // logger.error("Failed to authenticate email service", ex);
            throw new EmailSendingException("There was a problem sending the email. Please try again later.", ex.getCause());
        } catch (MessagingException ex) {
            // Log the messaging exception with a detailed message
            //logger.error("Failed to send message to " + email, ex);
            throw new EmailSendingException("There was a problem sending the email. Please try again later.", ex.getCause());
        }
    }

    public MimeMessage createMimeMessage(String email) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject(EmaiLConstans.ACCOUNT_VERIFICATION_SUBJECT);
            return message;
        } catch (MessagingException ex) {
            throw new EmailSendingException("There was a problem sending the email. Please try again later.", ex.getCause());
        }
    }

    public String createHtmlTemplate(String firstName, String Token){
        String htmlTemplate = EmaiLConstans.ACCOUNT_VERIFICATION_MESSAGE;
        htmlTemplate = htmlTemplate.replace("${name}", firstName);
        htmlTemplate = htmlTemplate.replace("${link}", EmaiLConstans.ACTIVATION_ENDPOINT + Token);
        return htmlTemplate;
    }
}


