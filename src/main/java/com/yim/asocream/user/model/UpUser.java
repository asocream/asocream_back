package com.yim.asocream.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@RequiredArgsConstructor
public class UpUser {

    private String userPw;
    private String newPw;

    public void PasswordEncode(PasswordEncoder passwordEncoder){
        this.userPw = passwordEncoder.encode(this.getUserPw());
        this.newPw = passwordEncoder.encode(this.getNewPw());
    }
}
