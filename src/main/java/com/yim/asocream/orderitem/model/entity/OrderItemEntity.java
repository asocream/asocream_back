package com.yim.asocream.orderitem.model.entity;

import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.model.common.BaseModel;
import com.yim.asocream.order.model.entity.OrderEntity;
import com.yim.asocream.orderitem.model.request.UpdOrderItemRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본생성자 생성 (접근지정자= PROTECTED)
public class OrderItemEntity extends BaseModel {

    @Id //id 프라머리키 설정,
    @GeneratedValue(strategy = GenerationType.IDENTITY)// GeneratedValue 자동 값증가 아이덴티티 전략
    @Column(name = "order_item_id")//이름 설정
    private long id;

    @OneToOne(fetch = FetchType.LAZY)//지연 로딩
    @JoinColumn(name = "item_id")
    private ItemEntity itemEntity;
    private int count;
    private long sumPrice;

    @ManyToOne(fetch = FetchType.LAZY)//지연 로딩
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    public OrderItemEntity(ItemEntity itemEntity, int count) {
        this.itemEntity = itemEntity;
        this.count = count;
        this.sumPrice = count*itemEntity.getPrice();
    }

    public void changeMe(UpdOrderItemRequest updOrderItemRequest,ItemEntity itemEntity){
        this.itemEntity = itemEntity;
        this.count = updOrderItemRequest.getCount();
        this.sumPrice = this.count*itemEntity.getPrice();
    }




}
