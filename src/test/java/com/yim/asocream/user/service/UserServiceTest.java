package com.yim.asocream.user.service;

import com.yim.asocream.model.user.Gender;
import com.yim.asocream.model.user.UserEntity;
import com.yim.asocream.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


class UserServiceTest {

    @Autowired UserService userService;




    @Test
    void findUserEmailAndUserEntity() {

    }

    @Test
    void confirmUser() {
    }

    @Test
    void insUser() {
        //given
        UserEntity userEntity = UserEntity.builder()
                .userEmail("yim010203@naver.com").userPw("q1w2e3r4t5!").name("임태형").gender(Gender.male).build();
        //when
        System.out.println(userEntity);
        //then


    }

    @Test
    void selUserOne() {
    }

    @Test
    void selUserList() {
    }

    @Test
    void userConfirm() {
    }

    @Test
    void upUser() {
    }

    @Test
    void delUser() {
    }
}