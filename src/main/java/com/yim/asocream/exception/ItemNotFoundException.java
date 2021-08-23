package com.yim.asocream.exception;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String msg){
        super(msg);
    }
    public ItemNotFoundException(Exception ex){
        super(ex);
    }
}
