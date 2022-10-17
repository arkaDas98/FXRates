package com.intuit.fxrates.exceptions;

public class JacksonException extends FXRatesException {

    public JacksonException(String message) {
        super(message);
    }

    public JacksonException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
