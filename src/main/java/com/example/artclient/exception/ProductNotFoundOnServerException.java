package com.example.artclient.exception;

public class ProductNotFoundOnServerException extends RuntimeException {
    public ProductNotFoundOnServerException(String message) {
        super(message);
    }
}
