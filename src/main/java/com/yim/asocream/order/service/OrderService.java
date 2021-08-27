package com.yim.asocream.order.service;

import com.yim.asocream.exception.OrderNotFoundException;
import com.yim.asocream.order.model.entity.OrderEntity;
import com.yim.asocream.order.model.request.InsOrderRequest;
import com.yim.asocream.order.model.request.UpdOrderRequest;
import com.yim.asocream.order.model.response.OrderResponse;
import com.yim.asocream.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public long insOrder(InsOrderRequest insOrderRequest) {

        OrderEntity orderEntity = insOrderRequest.changeEntity();

        orderRepository.save(orderEntity);

        return orderEntity.getId();
    }

    public OrderResponse selOrderOne(long orderId) {
        Optional<OrderEntity> orderEntity_ = orderRepository.findById(orderId);
        if(!orderEntity_.isPresent()){
            throw new OrderNotFoundException("없는 주문입니다.");
        }
        OrderEntity orderEntity = orderEntity_.get();
        OrderResponse orderResponse = new OrderResponse(orderEntity);

        return orderResponse;
    }

    public List<OrderResponse> selOrderList() {
        List<OrderEntity> orderEntities = orderRepository.findAll();
        return orderEntities.stream().map(orderEntity ->
                new OrderResponse(orderEntity)).collect(Collectors.toList());
    }


    public long updOrder(UpdOrderRequest updOrderRequest) {
        Optional<OrderEntity> orderEntity_ = orderRepository.findById(updOrderRequest.getOrderId());
        if(!orderEntity_.isPresent()){
            throw new OrderNotFoundException("없는 주문입니다.");
        }
        OrderEntity orderEntity = orderEntity_.get();
        orderEntity.changeAddress(updOrderRequest);
        return orderEntity.getId();
    }

    public long delOrder(long orderId) {
        Optional<OrderEntity> orderEntity_ = orderRepository.findById(orderId);
        if(!orderEntity_.isPresent()){
            throw new OrderNotFoundException("없는 주문입니다.");
        }
        OrderEntity orderEntity = orderEntity_.get();
        orderRepository.delete(orderEntity);
        return orderEntity.getId();
    }
}
