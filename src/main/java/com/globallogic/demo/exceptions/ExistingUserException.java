package com.globallogic.demo.exceptions;

public class ExistingUserException extends RuntimeException{
    public ExistingUserException(String message){
        super(message);
    }
}
