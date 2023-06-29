package com.shopwell.api.exceptions;

public class BrandAlreadyExistsException extends RuntimeException {

    public BrandAlreadyExistsException() {
    }

    public BrandAlreadyExistsException(String message) {
        super(message);
    }

    public BrandAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
