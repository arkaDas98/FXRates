package com.intuit.fxrates.controller;

import java.util.List;
import com.intuit.fxrates.component.RatesSetupComponent;
import com.intuit.fxrates.entity.ErrorResponse;
import com.intuit.fxrates.exceptions.ApiAccessException;
import com.intuit.fxrates.exceptions.JacksonException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api")
public class RatesSetupControllers {

    @Autowired
    private RatesSetupComponent component;

    @GetMapping("/supported")
    public ResponseEntity<List<String>> getSupportedCurrencies() {
        return ResponseEntity.ok(component.getSupportedCurrencies());
    }

    @PutMapping("/default")
    public void setDefaultRates(@RequestParam(defaultValue = "USD") String base,
            @RequestParam(required = false) List<String> symbols) {
        if (symbols == null) {
            component.setDefaultRates(base);
        } else {
            component.setDefaultRates(base, symbols);
        }
    }

    @ExceptionHandler(ApiAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleNoRecordFoundException(ApiAccessException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Error while accessing third-party api for default rates setup")
                .errorCode(401)
                .build();
        return errorResponse;
    }

    @ExceptionHandler(JacksonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleNoRecordFoundException(JacksonException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Error while unmarshalling response from third-party api")
                .errorCode(402)
                .build();
        return errorResponse;
    }
}
