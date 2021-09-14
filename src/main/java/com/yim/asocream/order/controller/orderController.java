package com.yim.asocream.order.controller;

import com.yim.asocream.model.common.Address;
import com.yim.asocream.order.model.request.InsOrderRequest;
import com.yim.asocream.order.model.request.UpdOrderRequest;
import com.yim.asocream.order.model.response.OrderResponse;
import com.yim.asocream.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class orderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public long insOrder(@RequestBody InsOrderRequest insOrderRequest){

        return orderService.insOrder(insOrderRequest);
    }


    @GetMapping("/order")
    public OrderResponse selOrderOne(Principal principal){

        return orderService.selOrderOne(principal);
    }

    @GetMapping("/orders")
    public List<OrderResponse> selOrderList(Principal principal){

        return orderService.selOrderList(principal);
    }

    @PatchMapping("/order")
    public long updOrder(@RequestBody UpdOrderRequest updOrderRequest,Principal principal){
        return orderService.updOrder(updOrderRequest,principal);
    }

    @DeleteMapping("/order")
    public long delOrder(long orderId){
        return orderService.delOrder(orderId);
    }

}
