package com.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandle {

    @ExceptionHandler({GlobalException.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public Object process(GlobalException ex){
        String result = ex.getDefaultMessage();
        System.out.println("#################: "+result);
        return ex;
    }
}
