package com.yim.asocream.user.service;


import com.yim.asocream.exception.UserOverlapException;
import com.yim.asocream.exception.WrongPasswordException;
import com.yim.asocream.user.model.entity.UserEntity;
import com.yim.asocream.user.model.ResponseUser;
import com.yim.asocream.user.model.UpUser;
import com.yim.asocream.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


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


    public boolean userConfirm(String userEmail) {
        return confirmUser(userEmail);
    }

    public long upUser(UpUser upUser, Principal principal) {
        UserEntity userEntity =
                findUserEmailAndUserEntity(principal.getName());
        upUser.PasswordEncode(passwordEncoder);

        if (!passwordEncoder.matches(upUser.getUserPw(), userEntity.getUserPw())) {//upUser.getUserPw() != userEntity.getUserPw()
            throw new WrongPasswordException("비밀번호가 틀렸습니다.");
        }
        userEntity.setUserPw(upUser.getNewPw());
        //userRepository.save(userEntity); 영속성 때매 아마 안적어줘도 될듯
        return userEntity.getId();
    }


    public long delUser(String userPw,Principal principal) {//!!!!업데이트의 일부요소와 중복됨 중복 따로 함수로
        UserEntity userEntity = findUserEmailAndUserEntity(principal.getName());
        if(!passwordEncoder.matches(userPw, userEntity.getUserPw())){
            throw new WrongPasswordException("비밀번호가 틀렸습니다.");
        }
        userRepository.delete(userEntity);
        return userEntity.getId();
    }


}


