package com.yim.asocream.orderitem.controller;

import com.yim.asocream.orderitem.model.request.InsOrderItemRequest;
import com.yim.asocream.orderitem.model.request.UpdOrderItemRequest;
import com.yim.asocream.orderitem.model.response.OrderItemResponse;
import com.yim.asocream.orderitem.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping("/orderItem")
    public long insOrderItem(@RequestBody InsOrderItemRequest insOrderItemRequest, Principal principal){
        return orderItemService.insOrderItem(insOrderItemRequest,principal);
    }
    @GetMapping("/orderItem")
    public OrderItemResponse selOrderItemOne(long orderItemId){

        return orderItemService.selOrderItemOne(orderItemId);
    }
    @PatchMapping("/orderItem")
    public long updOrderItem(@RequestBody UpdOrderItemRequest updOrderItemRequest){

        return orderItemService.updOrderItem(updOrderItemRequest);
    }

    @DeleteMapping("/orderItem")
    public long delOrderItem(long orderItemId){

        return orderItemService.delOrderItem(orderItemId);
    }

}
