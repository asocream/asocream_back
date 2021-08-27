package com.yim.asocream.order.model.request;

import com.yim.asocream.model.common.Address;
import com.yim.asocream.order.model.entity.OrderEntity;
import lombok.Getter;

@Getter
public class UpdOrderRequest {

    private long orderId;
    private Address address;//배송지 주소

}
