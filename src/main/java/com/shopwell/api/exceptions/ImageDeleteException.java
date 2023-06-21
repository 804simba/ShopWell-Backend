package com.shopwell.api.exceptions;

public class ImageDeleteException extends RuntimeException {
    public ImageDeleteException() {
        super();
    }

    public ImageDeleteException(String message) {
        super(message);
    }

    public ImageDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
