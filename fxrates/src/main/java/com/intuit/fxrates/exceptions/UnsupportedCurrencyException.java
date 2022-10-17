package com.intuit.fxrates.exceptions;

public class UnsupportedCurrencyException extends FXRatesException {

    public UnsupportedCurrencyException(String message) {
        super(message);
    }

    public UnsupportedCurrencyException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
