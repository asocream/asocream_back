package com.yim.asocream.user.controller;


import com.yim.asocream.common.FTPUploader;
import com.yim.asocream.user.model.InsUser;
import com.yim.asocream.user.model.ResponseUser;
import com.yim.asocream.user.model.UpUser;
import com.yim.asocream.user.service.EmailService;
import com.yim.asocream.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;


    /**
     * create
     */
    @PostMapping("/user")//회원가입
    public long insUser(@RequestBody InsUser insUser){
        System.out.println(insUser);
        return userService.insUser(insUser.changeEntity());
    }
    /**
     * read
     */
    @GetMapping("/user")//현재 로그인한 유저 정보
    public ResponseUser selUserOne(Principal principal){
        ResponseUser responseUser = userService.selUserOne(principal);
        return responseUser;
    }

    @GetMapping("/userConfirm")
    public boolean userConfirm(String userEmail){
        return userService.userConfirm(userEmail);
    }

    /**
     * update
     */
    @PatchMapping("/user")//비밀번호 변경
    public long updUser(@RequestBody UpUser upUser,Principal principal){
        return userService.upUser(upUser,principal);
    }

    /**
     * delete
     */
    @DeleteMapping("/user")//
    public long delUser(String userPw, Principal principal){

        return userService.delUser(userPw, principal);
    }

    //이메일 보내기
    @GetMapping("/sendMail")
    public void sendEmail(String userEmail){
        emailService.emailSend(userEmail);
    }

    // 회원가입 인증한 유저 권한 변경하기
    @PostMapping("/emailConfirm")
    public long emailConfirm(@RequestBody Token token) throws InterruptedException {
        return emailService.authenticationEmail(token.token);
    }

    //토큰 확인및 비밀번호 변경 봔환값 비밀번호임
    @PostMapping("/findPassword")
    public void findPassword(@RequestBody Token token) throws InterruptedException {
        emailService.findPassword(token.token);
    }

    @PatchMapping("/profile")//수정할꺼 엄청 많음 나중으로 좀 미루자
    public void updProfile(MultipartHttpServletRequest request,String uuid,Principal principal)throws Exception{

        userService.updProfile(request,uuid,principal);

    }

    @Getter
    static class Token{
        String token;
    }

}
