package com.yim.asocream.orderitem.model.request;
import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.orderitem.model.entity.OrderItemEntity;
import lombok.Getter;

@Getter
public class
UpdOrderItemRequest {

    private long orderItemId;
    private long itemId;
    private int count;



}
