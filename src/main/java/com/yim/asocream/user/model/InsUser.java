package com.yim.asocream.user.model;

import com.yim.asocream.user.model.entity.Gender;
import com.yim.asocream.user.model.entity.UserEntity;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class InsUser {

    @Email(message = "이메일 양식을 지켜주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String userEmail;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPw;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "성별을 선택해주세요.")
    private String gender;

    public UserEntity changeEntity(){
        Gender gender_;
        if(this.gender.equals("mail")){
            gender_= Gender.male;
        }
        else if(this.gender.equals("femail")){
            gender_ = Gender.female;
        }
        else {
            System.out.println(this.gender);
            throw new RuntimeException("정상적인 성별을 입력해주세요");
        }

        return UserEntity.
                builder()
                .userEmail(this.userEmail)
                .userPw(this.userPw)
                .name(this.name)
                .gender(gender_)
                .build();
    }

}