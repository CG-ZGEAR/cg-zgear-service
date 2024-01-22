package com.codegym.cgzgearservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import com.codegym.cgzgearservice.dto.payload.request.SendOtpRequest;
import com.codegym.cgzgearservice.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author ADMIN
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String mailFrom;

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendOtp(SendOtpRequest sendOtpRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(sendOtpRequest.getTo());
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + sendOtpRequest.getOtp());

        javaMailSender.send(message);
    }
}
