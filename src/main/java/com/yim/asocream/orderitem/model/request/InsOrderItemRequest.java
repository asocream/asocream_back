package com.yim.asocream.orderitem.model.request;
import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.orderitem.model.entity.OrderItemEntity;
import lombok.Getter;

@Getter
public class InsOrderItemRequest {

    private long itemId;
    private int count;

    public OrderItemEntity changeEntity(ItemEntity itemEntity){

        OrderItemEntity orderItemEntity = new OrderItemEntity(itemEntity,this.count);
        return orderItemEntity;
    }

}
