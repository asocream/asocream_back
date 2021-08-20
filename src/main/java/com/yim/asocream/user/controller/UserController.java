package com.yim.asocream.user.controller;


import com.yim.asocream.user.model.InsUser;
import com.yim.asocream.user.model.ResponseUser;
import com.yim.asocream.user.model.UpUser;
import com.yim.asocream.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * create
     */
    @PostMapping("/user")//회원가입
    public long insUser(@RequestBody InsUser insUser){
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
    @GetMapping("/userList") // !!!!!!!!전체 유저 검색 나중에 page 처리해야함
    public List<ResponseUser> selUserList(){

        return userService.selUserList();
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
    @PostMapping("/sendMail")
    public void sendEmail(@RequestBody String userEmail){
        userService.emailSend(userEmail);
    }






}
