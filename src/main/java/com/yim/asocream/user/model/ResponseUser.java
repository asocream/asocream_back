package com.yim.asocream.user.model;

import com.yim.asocream.user.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUser {

    private String userEmail;
    private String name;
    private String roles;
    private String gender;
    private String profile;

    @Builder
    public ResponseUser(UserEntity userEntity){
        this.userEmail = userEntity.getUserEmail();
        this.name = userEntity.getName();
        this.roles = userEntity.getRoleType().toString();
        this.gender = userEntity.getGender().toString();
        this.profile = userEntity.getProfileUrl();
    }

}
