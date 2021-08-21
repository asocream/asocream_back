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
        UserEntity userEntity = 
                findUserEmailAndUserEntity(principal.getName());//!!!!!트렌젝션 생명주기 확인
        upUser.PasswordEncode(passwordEncoder);
        if (upUser.getUserPw() != userEntity.getUserPw()) {
            throw new WrongPasswordException("비밀번호가 틀렸습니다.");
        }
        userEntity.setUserPw(upUser.getNewPw());
        //userRepository.save(userEntity); 영속성 때매 아마 안적어줘도 될듯
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

    public void emailSend(String userEmail) { //토큰 만들어서 이메일 인증번호 던짐

        String token = jwtTokenService.createToken(userEmail);//이메일로 만드러진 토큰

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);//보낼 이메일
        mailMessage.setSubject("회원가입 이메일 인증");
        mailMessage.setText("인증번호 :"+token);//jwt 를 이용해 인증번호 만듬
        emailService.sendEmail(mailMessage);

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


