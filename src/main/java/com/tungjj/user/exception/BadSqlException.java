package com.tungjj.user.exception;

public class BadSqlException extends RuntimeException {
    public BadSqlException(String message) {
        super(message);
    }
}
