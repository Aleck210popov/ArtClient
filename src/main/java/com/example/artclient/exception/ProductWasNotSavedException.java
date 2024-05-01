package com.example.artclient.exception;

public class ProductWasNotSavedException extends RuntimeException {
    public ProductWasNotSavedException(String message) {
        super(message);
    }
}
