package com.yim.asocream.orderitem.service;

import com.yim.asocream.exception.ItemNotFoundException;
import com.yim.asocream.exception.OrderItemNotFoundException;
import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.item.repository.ItemRepository;
import com.yim.asocream.order.model.entity.OrderEntity;
import com.yim.asocream.order.repository.OrderRepository;
import com.yim.asocream.orderitem.model.entity.OrderItemEntity;
import com.yim.asocream.orderitem.model.request.InsOrderItemRequest;
import com.yim.asocream.orderitem.model.request.UpdOrderItemRequest;
import com.yim.asocream.orderitem.model.response.OrderItemResponse;
import com.yim.asocream.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;


    public long insOrderItem(InsOrderItemRequest insOrderItemRequest) {

        Optional<ItemEntity> itemEntity_ = itemRepository.findById(insOrderItemRequest.getItemId());
        if(!itemEntity_.isPresent()){
            throw new ItemNotFoundException("없는 물품 입니다.");
        }
        ItemEntity itemEntity = itemEntity_.get();
        OrderItemEntity orderItemEntity = insOrderItemRequest.changeEntity(itemEntity);
        orderItemRepository.save(orderItemEntity);
        //order 의 sumPrice 변경 지연 로딩 전략 할필요 있을까??? 뒤에 얼마나 쓰는지 확인해보자
        OrderEntity orderEntity = orderItemEntity.getOrderEntity();
        orderEntity.addSumPrice(orderItemEntity.getSumPrice());
        return orderItemEntity.getId();
    }

    public OrderItemResponse selOrderItemOne(long orderItemId) {
        Optional<OrderItemEntity> orderItemEntity_ = orderItemRepository.findById(orderItemId);

        if(!orderItemEntity_.isPresent()){
            throw new OrderItemNotFoundException("없는 oderItem 입니다.");
        }
        OrderItemEntity orderItemEntity = orderItemEntity_.get();
        return new OrderItemResponse(orderItemEntity);
    }

    public long updOrderItem(UpdOrderItemRequest updOrderItemRequest) {

        Optional<OrderItemEntity> orderItemEntity_ =
                orderItemRepository.findById(updOrderItemRequest.getOrderItemId());
        if(!orderItemEntity_.isPresent()){
            throw new OrderItemNotFoundException("없는 oderItem 입니다.");
        }
        OrderItemEntity orderItemEntity = orderItemEntity_.get();

        Optional<ItemEntity> itemEntity_ = itemRepository.findById(updOrderItemRequest.getItemId());

        if(!itemEntity_.isPresent()){
            throw new ItemNotFoundException("없는 물품 입니다.");
        }
        orderItemEntity.changeMe(updOrderItemRequest,itemEntity_.get());
        return orderItemEntity.getId();
    }

    public long delOrderItem(long orderItemId) {

        Optional<OrderItemEntity> orderItemEntity_ = orderItemRepository.findById(orderItemId);
        if(!orderItemEntity_.isPresent()){
            throw new OrderItemNotFoundException("없는 oderItem 입니다.");
        }
        OrderItemEntity orderItemEntity = orderItemEntity_.get();

        orderItemRepository.delete(orderItemEntity);
        return orderItemEntity.getId();
    }
}
