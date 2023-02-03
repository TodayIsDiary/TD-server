package com.example.todayisdiary.global.error.handler;

import com.example.todayisdiary.global.error.entity.ErrorResponseEntity;
import com.example.todayisdiary.global.error.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e){
        return ErrorResponseEntity.responseEntity(e.getErrorCode());
    }
}
