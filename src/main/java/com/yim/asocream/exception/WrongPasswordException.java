package com.yim.asocream.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(String msg){
        super(msg);
    }
    public WrongPasswordException(Exception ex){
        super(ex);
    }
}
