package com.yim.asocream.orderitem.model.response;

import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.item.model.response.ItemResponse;
import com.yim.asocream.orderitem.model.entity.OrderItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor
public class OrderItemResponse {

    private long id;
    private ItemResponse itemResponse;
    private int count;
    private long sumPrice;


    public OrderItemResponse(OrderItemEntity orderItemEntity){
        this.id = orderItemEntity.getId();
        this.itemResponse = new ItemResponse(orderItemEntity.getItemEntity()) ;
        this.count = orderItemEntity.getCount();
        this.sumPrice = orderItemEntity.getSumPrice();

    }
}
