package com.yim.asocream.order.model.entity;

import com.yim.asocream.model.common.Address;
import com.yim.asocream.order.model.request.UpdOrderRequest;
import com.yim.asocream.orderitem.model.entity.OrderItemEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)//기본생성자 생성 (접근지정자= PROTECTED)
@Getter
public class OrderEntity {

    @Id //id 프라머리키 설정,
    @GeneratedValue(strategy = GenerationType.IDENTITY)// GeneratedValue 자동 값증가 아이덴티티 전략
    @Column(name = "order_id")//이름 설정
    private long id;

    @Embedded
    private Address address;//배송지 주소
    @Enumerated(EnumType.STRING)
    private OrderState orderState;//배송상태
    private int invoiceNumber;//송장번호 배송상태가 바뀔때 넣어주자

    @OneToMany(mappedBy = "orderEntity")
    private List<OrderItemEntity> orderItemEntities;

    private long sumPrice = 0;//총가격 가격은 orderItem이 추가될때마다 생김

    public void changeAddress(UpdOrderRequest updOrderRequest) {
        this.address = updOrderRequest.getAddress();
    }

    public OrderEntity(Address address){

        this.address = address;
        this.orderState = OrderState.RECEIPT;

    }
    public void addSumPrice(long sumPrice) {
        this.sumPrice += sumPrice;
    }
}
