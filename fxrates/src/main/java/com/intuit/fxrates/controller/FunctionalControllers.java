package com.intuit.fxrates.controller;

import java.util.List;

import com.intuit.fxrates.component.ExchangesComponent;
import com.intuit.fxrates.entity.Conversions;
import com.intuit.fxrates.entity.Counter;
import com.intuit.fxrates.entity.ErrorResponse;
import com.intuit.fxrates.exceptions.UnsupportedCurrencyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api")
public class FunctionalControllers {

    @Autowired
    private ExchangesComponent component;

    @GetMapping("/fxpair/{currencyPairs}")
    public ResponseEntity<List<Conversions>> getConversions(@PathVariable(required = true) String currencyPairs) {
        return ResponseEntity.ok(component.getAllConversions(currencyPairs));
    }

    @GetMapping("/fxpair/top/{count}")
    public ResponseEntity<List<Counter>> getConversions(@PathVariable(required = true) int count) {
        return ResponseEntity.ok(component.getTopMostRequestedPairs(count));
    }

    @ExceptionHandler(UnsupportedCurrencyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNoRecordFoundException(UnsupportedCurrencyException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Unsupported/invalid currency code passed as input")
                .errorCode(501)
                .build();
        return errorResponse;
    }
}
