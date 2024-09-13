package com.example.spb_api.exception;

import com.example.spb_api.message.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WeatherExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleAllException() {
        return new ErrorMessage(10000, "No information available!");
    }
}
