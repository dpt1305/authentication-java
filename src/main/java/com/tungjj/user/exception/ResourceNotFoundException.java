package com.tungjj.user.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String messString) {
        super(messString);
    }
}
