package com.yim.asocream.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender javaMailSender;

    @Async//!!!!뭔지 까먹음 찾아보자
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }



}
