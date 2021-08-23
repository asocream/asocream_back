package com.yim.asocream.exception;

public class OrderItemNotFoundException extends RuntimeException{
    public OrderItemNotFoundException(String msg){
        super(msg);
    }
    public OrderItemNotFoundException(Exception ex){
        super(ex);
    }
}
