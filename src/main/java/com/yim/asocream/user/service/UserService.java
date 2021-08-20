package com.yim.asocream.user.service;


import com.yim.asocream.exception.UserOverlapException;
import com.yim.asocream.exception.WrongPasswordException;
import com.yim.asocream.model.user.UserEntity;
import com.yim.asocream.user.model.ResponseUser;
import com.yim.asocream.user.model.UpUser;
import com.yim.asocream.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtTokenService jwtTokenService;

    public UserEntity findUserEmailAndUserEntity(String userEmail) { //이메일을 통해 아이디 찾아 엔티티로 반환

        Optional<UserEntity> userEntity_ = userRepository.findOptionalByUserEmail(userEmail);
        if (!userEntity_.isPresent()) {
            throw new UsernameNotFoundException("없는 이메일 입니다.");
        }
        return userEntity_.get();
    }

    public boolean confirmUser(String userEmail) { //이메일 중복확인
        Optional<UserEntity> userEntity_ = userRepository.findOptionalByUserEmail(userEmail);
        if (userEntity_.isPresent()) {
            throw new UserOverlapException("중복된 이메일 입니다.");
        }
        return true;
    }


    public long insUser(UserEntity userEntity) {

        confirmUser(userEntity.getUserEmail());
        userEntity.userPasswordEncoder(passwordEncoder);
        userRepository.save(userEntity);//저장해주는 동시에 id 값 받아와 userEntity 저장해둠
        return userEntity.getId();
    }


    public ResponseUser selUserOne(Principal principal) {

        ResponseUser responseUser = ResponseUser.builder()
                .userEntity(findUserEmailAndUserEntity(principal.getName())).build();
        return responseUser;
    }

    public List<ResponseUser> selUserList() {

        List<ResponseUser> responseUserList = new ArrayList<>();
        List<UserEntity> userEntityList = userRepository.findAll();
        userEntityList.stream().map(userEntity ->
                ResponseUser.builder().userEntity(userEntity).build());


        return responseUserList;
    }

    public boolean userConfirm(String userEmail) {
        return confirmUser(userEmail);
    }

    public long upUser(UpUser upUser, Principal principal) {
        UserEntity userEntity = findUserEmailAndUserEntity(principal.getName());//!!!!!함수로 사용시 상태를 유지하는지 모르겠다
        upUser.PasswordEncode(passwordEncoder);
        if (upUser.getUserPw() != userEntity.getUserPw()) {
            throw new WrongPasswordException("비밀번호가 틀렸습니다.");
        }
        userEntity.setUserPw(upUser.getNewPw());
        userRepository.save(userEntity);
        return userEntity.getId();
    }


    public long delUser(String password,Principal principal) {//!!!!업데이트의 일부요소와 중복됨 중복 따로 함수로
        UserEntity userEntity = findUserEmailAndUserEntity(principal.getName());
        String userPw = passwordEncoder.encode(password);
        if(userPw != userEntity.getUserPw()){
            throw new WrongPasswordException("비밀번호가 틀렸습니다.");
        }
        userRepository.delete(userEntity);
        return userEntity.getId();
    }

    public void emailSend(String userEmail) {

        String token = jwtTokenService.createToken(userEmail);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);//보낼 이메일
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("인증번호 :"+token);//jwt 를 이용해 인증번호 만듬
        emailService.sendEmail(mailMessage);

    }

    public void authenticationEmail(String token) throws InterruptedException {

        String userEmail = jwtTokenService.authenticationToken(token);
        Optional<UserEntity> userEntity_ =
                userRepository.findOptionalByUserEmail(userEmail);
        if(!userEntity_.isPresent()){
            throw new UsernameNotFoundException("없는 이메일 입니다.");
        }
        UserEntity userEntity = userEntity_.get();
        userEntity.setRoles("user");
        userRepository.save(userEntity);

    }
}


