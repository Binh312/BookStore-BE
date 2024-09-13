package com.bookstore.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalException extends RuntimeException{

    private String defaultMessage;

    private int errorCode;

    public GlobalException(String message, int errorCode){
        this.defaultMessage = message;
        this.errorCode = errorCode;
    }

    public GlobalException(String message){
        this.defaultMessage = message;
        this.errorCode = 500;
    }
}
