package com.yim.asocream.exception;

public class UserOverlapException extends RuntimeException{
    public UserOverlapException(String msg){
        super(msg);
    }
    public UserOverlapException(Exception ex){
        super(ex);
    }
}
