package com.yim.asocream.user.service;

import com.yim.asocream.user.model.entity.Gender;
import com.yim.asocream.user.model.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


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