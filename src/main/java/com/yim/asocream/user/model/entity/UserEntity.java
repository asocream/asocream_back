package com.yim.asocream.user.model.entity;

import com.yim.asocream.model.common.BaseTimeModel;
import com.yim.asocream.user.model.RoleType;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity//jpa entity 등록
@Getter//getter 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본생성자 생성 (접근지정자= PROTECTED)
public class
UserEntity extends BaseTimeModel {

    @Id //id 프라머리키 설정,
    @GeneratedValue(strategy = GenerationType.IDENTITY)// GeneratedValue 자동 값증가 아이덴티티 전략
    @Column(name = "user_id")//이름 설정
    private long id;

    private String userEmail;//유저 아이디
    private String userPw;
    private String name;
    private String profileUrl = "/profile/common/default.jpg";

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.UNAUTHENTICATED;

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    @Builder
    public UserEntity(String userEmail, String userPw, String name, Gender gender) {
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.name = name;
        this.gender = gender;
    }

    public void userPasswordEncoder(PasswordEncoder passwordEncoder){
        this.userPw = passwordEncoder.encode(this.getUserPw());
    }

}