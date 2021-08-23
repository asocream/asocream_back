package com.yim.asocream.orderitem.controller;

import com.yim.asocream.orderitem.model.request.InsOrderItemRequest;
import com.yim.asocream.orderitem.model.response.OrderItemResponse;
import com.yim.asocream.orderitem.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/orderItem")
    public long insOrderItem(@RequestBody InsOrderItemRequest insOrderItemRequest){
        return orderItemService.insOrderItem(insOrderItemRequest);
    }
    @GetMapping("/orderItem")
    public OrderItemResponse selOrderItemOne(long orderItemId){
        return orderItemService.selOrderItemOne(orderItemId);
    }
}
