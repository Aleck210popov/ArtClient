package com.example.artclient.exception;

public class StringIsEmptyException extends RuntimeException {
    public StringIsEmptyException(String message) {
        super(message);
    }
}
