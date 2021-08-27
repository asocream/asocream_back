package com.yim.asocream.order.repository;

import com.yim.asocream.order.model.entity.OrderEntity;
import com.yim.asocream.order.model.request.InsOrderRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {


}
