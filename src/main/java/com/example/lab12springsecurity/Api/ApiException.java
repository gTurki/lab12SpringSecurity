package com.example.lab12springsecurity.Api;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
