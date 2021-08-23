package com.yim.asocream.orderitem.model.entity;

import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.model.common.BaseModel;
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

    @ManyToOne(fetch = FetchType.LAZY)//지연 로딩
    @JoinColumn(name = "itemEntity_id")
    private ItemEntity itemEntity;
    private int count;
    private long sumPrice;

    public OrderItemEntity(ItemEntity itemEntity, int count) {
        this.itemEntity = itemEntity;
        this.count = count;
        this.sumPrice = count*itemEntity.getPrice();
    }

}
