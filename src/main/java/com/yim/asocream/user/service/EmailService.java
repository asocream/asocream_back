package com.yim.asocream.user.service;

import com.yim.asocream.model.user.UserEntity;
import com.yim.asocream.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender javaMailSender;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Async//!!!!뭔지 까먹음 찾아보자
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void emailSend(String userEmail) {

        String token = jwtTokenService.createToken(userEmail);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);//보낼 이메일
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("인증번호 :"+token);//jwt 를 이용해 인증번호 만듬
        sendEmail(mailMessage);
    }

    public String findPassword(String token) throws InterruptedException{
        String userEmail = jwtTokenService.authenticationToken(token);

        Optional<UserEntity> userEntity_ =
                userRepository.findOptionalByUserEmail(userEmail);
        if(!userEntity_.isPresent()){
            throw new UsernameNotFoundException("업는 아이디 입니다.");
        }
        UserEntity userEntity = userEntity_.get();
        String newPassword = getRamdomPassword();
        userEntity.setUserPw(newPassword);
        userEntity.userPasswordEncoder(passwordEncoder);
        userRepository.save(userEntity);
        return newPassword;
    }

    public String getRamdomPassword() {
        int len = 12;
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };
        int idx = 0; StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            idx = (int) (charSet.length * Math.random()); // 36 * 생성된 난수를 Int로 추출 (소숫점제거)
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }






}
