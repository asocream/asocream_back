package com.yim.asocream.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String msg){
        super(msg);
    }
    public OrderNotFoundException(Exception ex){
        super(ex);
    }
}
