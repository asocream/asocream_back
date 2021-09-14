package com.yim.asocream.order.repository;

import com.yim.asocream.order.model.entity.OrderEntity;
import com.yim.asocream.order.model.entity.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    Optional<OrderEntity> findOptionalByCreatedByAndOrderState(String createdBy, OrderState orderState);
    List<OrderEntity> findOptionalByCreatedByAndOrderStateNot(String createdBy, OrderState orderState);
}
