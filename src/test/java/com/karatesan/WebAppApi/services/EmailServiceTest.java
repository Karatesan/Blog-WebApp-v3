package com.karatesan.WebAppApi.services;


import com.karatesan.WebAppApi.utility.EmaiLConstans;
import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    @Mock
    MimeMessage mimeMessage;

    @InjectMocks
    EmailService emailService;

    @Test
    public void createMimeMessage_shouldReturnPreparedEmailContent_whenProvidedWithRecipient() throws MessagingException {
        String recipient = "test@email.com";
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        when(mimeMessage.getAllRecipients()).thenReturn(new Address[]{new InternetAddress(recipient)});
        when(mimeMessage.getSubject()).thenReturn(EmaiLConstans.ACCOUNT_VERIFICATION_SUBJECT);

        MimeMessage message = emailService.createMimeMessage(recipient);

        assertThat(message.getAllRecipients()[0].toString()).isEqualTo(recipient);
        assertThat(message.getSubject()).isEqualTo(EmaiLConstans.ACCOUNT_VERIFICATION_SUBJECT);
    }





}
