package com.yim.asocream.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }
    public UserNotFoundException(Exception ex){
        super(ex);
    }
}
