package com.yagizeris.springsecurityjwtexample.common.exceptions;

public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
