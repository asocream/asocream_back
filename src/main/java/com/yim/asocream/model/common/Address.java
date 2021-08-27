package com.yim.asocream.model.common;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    String zipcode;
    String shortAddress;
    String detailedAddress;


    public Address(String zipcode,String detailedAddress){
        this.zipcode = zipcode;
        this.detailedAddress = detailedAddress;
        //this.shortAddress = change(zipcode);  집코드 변환해서 넣기
    }
}
