package com.yim.asocream.user.service;

import com.yim.asocream.user.model.entity.UserEntity;
import com.yim.asocream.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailService {


    private final JavaMailSender javaMailSender;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Async//!!!!비동기 처리인거 같은데 좀더 공부해서 내껄로 만들기
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

    public long authenticationEmail(String token) throws InterruptedException {

        String userId = jwtTokenService.authenticationToken(token);
        Optional<UserEntity> userEntity_ =
                userRepository.findOptionalByUserEmail(userId);
        if(!userEntity_.isPresent()){
            throw new UsernameNotFoundException("없는 아이디 입니다.");
        }
        UserEntity userEntity = userEntity_.get();
        userEntity.setRoles("user");
        //userRepository.save(userEntity); 영속성 때매 안해줘도 되는데 실수로 @Transactional 안넣어서 엄청 찾음 ㅠㅠ
        return userEntity.getId();
    }



    public String findPassword(String token) throws InterruptedException{

        String userEmail = jwtTokenService.authenticationToken(token);
        Optional<UserEntity> userEntity_ =
                userRepository.findOptionalByUserEmail(userEmail);
        if(!userEntity_.isPresent()){
            throw new UsernameNotFoundException("없는 아이디 입니다.");
        }
        UserEntity userEntity = userEntity_.get();
        String newPassword = getRamdomPassword();
        userEntity.setUserPw(newPassword);
        userEntity.userPasswordEncoder(passwordEncoder);
        //userRepository.save(userEntity);영속성 나란 병신은 왜 지워도 될걸 주석달까?? 누군가는 이 주석 봐주면서 피식 해줬음~~~~
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
