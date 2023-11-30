package com.example.bankingapp.exceptions;

public class IncorrectLimitException extends RuntimeException {
    public IncorrectLimitException(String message) {
        super(message);
    }
}
