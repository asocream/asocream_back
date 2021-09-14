package com.yim.asocream.item.model.response;

import com.yim.asocream.item.model.entity.ItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private long id;
    private String title;
    private String Explanation;
    private int price;
    private long views;
    private String url;

    public ItemResponse(ItemEntity itemEntity){
        this.id = itemEntity.getId();
        this.title = itemEntity.getTitle();
        this.Explanation = itemEntity.getExplanation();
        this.price = itemEntity.getPrice();
        this.views = itemEntity.getViews();
        this.url = itemEntity.getUrl();
    }
}
