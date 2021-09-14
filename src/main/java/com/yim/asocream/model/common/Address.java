package com.yim.asocream.model.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {

    String zipcode;
    String shortAddress;
    String detailedAddress;


    public Address(String zipcode,String detailedAddress,String shortAddress){
        this.zipcode = zipcode;
        this.detailedAddress = detailedAddress;
        this.shortAddress = shortAddress;
    }
}
