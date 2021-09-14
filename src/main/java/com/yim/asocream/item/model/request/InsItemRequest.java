package com.yim.asocream.item.model.request;

import com.yim.asocream.item.model.entity.ItemEntity;
import lombok.Getter;

@Getter
public class InsItemRequest {

    private String title;
    private String explanation;//설명
    private int price;
    private long kindId;

    public ItemEntity changeItemEntity(){
        return new ItemEntity(title,explanation,price);
    }

}
