package com.yim.asocream.user.model;

import com.yim.asocream.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUser {

    private String userEmail;
    private String name;
    private String roles;

    @Builder
    public ResponseUser(UserEntity userEntity){
        this.userEmail = userEntity.getUserEmail();
        this.name = userEntity.getName();
        this.roles = userEntity.getRoles();
    }

}
