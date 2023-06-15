package com.shopwell.api.exceptions.advice;

import com.simba.shopwell.exceptions.ProductNotFoundException;
import com.simba.shopwell.model.DTOs.response.ErrorMessageVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFound(ProductNotFoundException exception) {
        var errorMessageVO = ErrorMessageVO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageVO);
    }
}
