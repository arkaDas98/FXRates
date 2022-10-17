package com.intuit.fxrates.exceptions;

public class ApiAccessException extends FXRatesException {

    public ApiAccessException(String message) {
        super(message);
    }

    public ApiAccessException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}