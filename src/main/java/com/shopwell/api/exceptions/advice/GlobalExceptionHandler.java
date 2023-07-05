package com.shopwell.api.exceptions.advice;

import com.shopwell.api.exceptions.ImageDeleteException;
import com.shopwell.api.exceptions.ImageUploadException;
import com.shopwell.api.exceptions.ProductNotFoundException;
import com.shopwell.api.exceptions.ProductOutOfStockException;
import com.shopwell.api.model.VOs.response.ErrorMessageVO;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<?> imageUploadException(ImageUploadException exception) {
        var errorMessageVO = ErrorMessageVO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageVO);
    }

    @ExceptionHandler(ImageDeleteException.class)
    public ResponseEntity<?> imageDeleteException(ImageDeleteException exception) {
        var errorMessageVO = ErrorMessageVO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessageVO);
    }

    @ExceptionHandler(SchedulerException.class)
    public ResponseEntity<?> schedulerException(SchedulerException exception) {
        var errorMessageVO = ErrorMessageVO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorMessageVO);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> schedulerException(UsernameNotFoundException exception) {
        var errorMessageVO = ErrorMessageVO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageVO);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> schedulerException(NullPointerException exception) {
        var errorMessageVO = ErrorMessageVO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageVO);
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<?> schedulerException(ProductOutOfStockException exception) {
        var errorMessageVO = ErrorMessageVO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageVO);
    }
}
