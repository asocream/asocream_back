package com.yim.asocream.order.service;

import com.yim.asocream.exception.OrderNotFoundException;
import com.yim.asocream.order.model.entity.OrderEntity;
import com.yim.asocream.order.model.entity.OrderState;
import com.yim.asocream.order.model.request.InsOrderRequest;
import com.yim.asocream.order.model.request.UpdOrderRequest;
import com.yim.asocream.order.model.response.OrderResponse;
import com.yim.asocream.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.security.Principal;
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

    public OrderResponse selOrderOne(Principal principal) {
        System.out.println(principal);
        Optional<OrderEntity> orderEntity_ =
                orderRepository.findOptionalByCreatedByAndOrderState(principal.getName(), OrderState.RECEIPT);
        if(!orderEntity_.isPresent()){
            throw new OrderNotFoundException("없는 주문입니다.");
        }
        OrderEntity orderEntity = orderEntity_.get();
        OrderResponse orderResponse = new OrderResponse(orderEntity);
        return orderResponse;
    }

    public List<OrderResponse> selOrderList(Principal principal) {

        List<OrderEntity> orderEntities =
                orderRepository.findOptionalByCreatedByAndOrderStateNot(principal.getName(),OrderState.RECEIPT);
        return orderEntities.stream().map(orderEntity ->
                new OrderResponse(orderEntity)).collect(Collectors.toList());
    }


    public long updOrder(UpdOrderRequest updOrderRequest, Principal principal) {
        Optional<OrderEntity> orderEntity_ =
                orderRepository.findOptionalByCreatedByAndOrderState(principal.getName(), OrderState.RECEIPT);
        if(!orderEntity_.isPresent()){
            throw new OrderNotFoundException("없는 주문입니다.");
        }
        OrderEntity orderEntity = orderEntity_.get();
        orderEntity.setOrderState(OrderState.DELIVERY);
        orderEntity.changeAddress(updOrderRequest);

        ///산 물품들 죄회수 1씩 증가
        orderEntity.getOrderItemEntities().stream().forEach(
                orderItemEntity -> orderItemEntity.getItemEntity().addViews(orderItemEntity.getCount()));

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
