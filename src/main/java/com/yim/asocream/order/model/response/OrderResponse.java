package com.yim.asocream.order.model.response;

import com.yim.asocream.model.common.Address;
import com.yim.asocream.order.model.entity.OrderEntity;
import com.yim.asocream.order.model.entity.OrderState;
import com.yim.asocream.orderitem.model.entity.OrderItemEntity;
import com.yim.asocream.orderitem.model.response.OrderItemResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OrderResponse {

    private long id;
    private Address address;//배송지 주소
    private OrderState orderState;//배송상태
    private int invoiceNumber;//송장번호 배송상태가 바뀔때 넣어주자
    private List<OrderItemResponse> orderItemResponses;//이거 보낼때 id 값만 보내줘야하나 다보내줘야하나 안보내줘야하나 고민중
    private long sumPrice;//총가격 가격은 orderItem이 추가될때마다 생김

    public OrderResponse(OrderEntity orderEntity){
        this.id = orderEntity.getId();
        this.address = orderEntity.getAddress();
        this.orderItemResponses = orderEntity.getOrderItemEntities().stream()
                .map(orderItemEntity -> new OrderItemResponse(orderItemEntity)).collect(Collectors.toList());
        this.orderState = orderEntity.getOrderState();
        this.sumPrice = orderEntity.getSumPrice();
        this.invoiceNumber = orderEntity.getInvoiceNumber();
    }

}
