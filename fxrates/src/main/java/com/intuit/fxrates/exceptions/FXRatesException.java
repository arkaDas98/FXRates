package com.intuit.fxrates.exceptions;

public class FXRatesException extends RuntimeException {

    public FXRatesException(Throwable cause) {
        initCause(cause);
    }

    public FXRatesException(String message) {
        super(message);
    }

    public FXRatesException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}