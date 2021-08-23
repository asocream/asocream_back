package com.yim.asocream.orderitem.model.response;

import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.orderitem.model.entity.OrderItemEntity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OrderItemResponse {

    private long id;
    private ItemEntity itemEntity;
    private int count;
    private long sumPrice;


    public OrderItemResponse(OrderItemEntity orderItemEntity){
        this.id = orderItemEntity.getId();
        this.itemEntity = orderItemEntity.getItemEntity();
        this.count = orderItemEntity.getCount();
        this.sumPrice = orderItemEntity.getSumPrice();

    }
}
