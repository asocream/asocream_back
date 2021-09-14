package com.yim.asocream.orderitem.service;

import com.yim.asocream.exception.ItemNotFoundException;
import com.yim.asocream.exception.OrderItemNotFoundException;
import com.yim.asocream.item.model.entity.ItemEntity;
import com.yim.asocream.item.repository.ItemRepository;
import com.yim.asocream.model.common.Address;
import com.yim.asocream.order.model.entity.OrderEntity;
import com.yim.asocream.order.model.entity.OrderState;
import com.yim.asocream.order.repository.OrderRepository;
import com.yim.asocream.orderitem.model.entity.OrderItemEntity;
import com.yim.asocream.orderitem.model.request.InsOrderItemRequest;
import com.yim.asocream.orderitem.model.request.UpdOrderItemRequest;
import com.yim.asocream.orderitem.model.response.OrderItemResponse;
import com.yim.asocream.orderitem.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;


    public long insOrderItem(InsOrderItemRequest insOrderItemRequest,Principal principal) {

        Optional<ItemEntity> itemEntity_ = itemRepository.findById(insOrderItemRequest.getItemId());
        if(!itemEntity_.isPresent()){
            throw new ItemNotFoundException("없는 물품 입니다.");
        }
        ItemEntity itemEntity = itemEntity_.get();
        OrderItemEntity orderItemEntity = insOrderItemRequest.changeEntity(itemEntity);

        //여기서 orderEntity 가 유저아이디랑 같으면서 그리고 주문상태가 대기인것을 가져와서 없으면 만들어야함
        OrderEntity orderEntity;
        Optional<OrderEntity> orderEntity_ =
                orderRepository.findOptionalByCreatedByAndOrderState(principal.getName(), OrderState.RECEIPT);
        if(orderEntity_.isPresent()){
            orderEntity = orderEntity_.get();
        }
        else {
            orderEntity = new OrderEntity();
        }
        orderRepository.save(orderEntity);
        orderItemEntity.setOrderEntity(orderEntity);
        orderItemRepository.save(orderItemEntity);
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
