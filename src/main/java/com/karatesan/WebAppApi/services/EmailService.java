package com.karatesan.WebAppApi.services;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
}
