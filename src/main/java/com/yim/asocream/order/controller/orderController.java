package com.yim.asocream.order.controller;

import com.yim.asocream.model.common.Address;
import com.yim.asocream.order.model.request.InsOrderRequest;
import com.yim.asocream.order.model.request.UpdOrderRequest;
import com.yim.asocream.order.model.response.OrderResponse;
import com.yim.asocream.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public OrderResponse selOrderOne(long orderId){
        return orderService.selOrderOne(orderId);
    }

    @GetMapping("/orders")
    public List<OrderResponse> selOrderList(){
        return orderService.selOrderList();
    }
    @PatchMapping("/order")
    public long updOrder(@RequestBody UpdOrderRequest updOrderRequest){
        return orderService.updOrder(updOrderRequest);
    }

    @DeleteMapping("/order")
    public long delOrder(long orderId){
        return orderService.delOrder(orderId);
    }

}
