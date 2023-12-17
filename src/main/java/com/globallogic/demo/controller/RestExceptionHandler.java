package com.globallogic.demo.controller;

import com.globallogic.demo.model.dto.response.Error;
import com.globallogic.demo.model.dto.response.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ResponseBody
    @ExceptionHandler( NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Error notFoundHandler(NoSuchElementException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Error fieldErrorHandler(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return buildErrorResponse(HttpStatus.BAD_REQUEST.value(), errors);
    }

    private Error buildErrorResponse(int code, String message){
        ErrorDetail response = new ErrorDetail();
        response.setTimestamp(Instant.now());
        response.setCode(code);
        response.setDetail(message);

        List<ErrorDetail> list = new ArrayList<>();
        list.add(response);

        Error errorResponse = new Error();
        errorResponse.setErrors(list);

        return errorResponse;
    }
}
