package com.yim.asocream.item.model.request;

import com.yim.asocream.item.model.entity.ItemEntity;
import lombok.Getter;

@Getter
public class InsItemRequest {

    private String title;
    private String Explanation;
    private int price;

    public ItemEntity changeItemEntity(){
        return new ItemEntity(title,Explanation,price);
    }

}
