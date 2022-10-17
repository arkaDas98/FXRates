package com.intuit.fxrates.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.intuit.fxrates.util.BigDecimalUtil;
import lombok.Data;
import lombok.NonNull;

@Data
public class Conversions {

    private String baseCurrency;
    private BigDecimal baseValue;
    private Map<String, BigDecimal> convertedCurrencies;

    public Conversions(String base, BigDecimal value) {
        this.baseCurrency = base;
        this.baseValue = value;
        convertedCurrencies = new HashMap<>();
    }

    public void addCurrencyUsingExchangeRate(@NonNull final String currency,
            @NonNull final BigDecimal baseToCurrencyRate) {
        convertedCurrencies.put(currency, BigDecimalUtil.multiply(baseValue, baseToCurrencyRate));
    }
}
