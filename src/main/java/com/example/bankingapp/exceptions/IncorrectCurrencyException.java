package com.example.bankingapp.exceptions;

public class IncorrectCurrencyException extends RuntimeException {
    public IncorrectCurrencyException(String message) {
        super(message);
    }
}
