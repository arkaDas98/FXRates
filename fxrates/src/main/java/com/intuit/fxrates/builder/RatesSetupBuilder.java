package com.intuit.fxrates.builder;

import java.util.List;

import com.intuit.fxrates.accessor.FXRatesAccessor;
import com.intuit.fxrates.builder.ratesSetupStrategy.RatesSetupStrategy;
import com.intuit.fxrates.dto.FXRatesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component
public class RatesSetupBuilder {

    @Autowired
    private FXRatesAccessor accessor;
    @Autowired
    private RatesSetupStrategy strategy;

    public void setupDefaultRates(@NonNull final String base) {
        final FXRatesDTO fxRates = accessor.fetchLatestRates(base);
        strategy.persistRates(fxRates);
    }

    public void setupDefaultRates(@NonNull final String base, @NonNull final List<String> supportedCurrencyList) {
        final FXRatesDTO fxRates = accessor.fetchLatestRates(base, supportedCurrencyList);
        strategy.persistRates(fxRates);
    }
}
