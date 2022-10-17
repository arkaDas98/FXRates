package com.intuit.fxrates.builder.ratesSetupStrategy.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.intuit.fxrates.builder.ratesSetupStrategy.RatesSetupStrategy;
import com.intuit.fxrates.dto.FXRatesDTO;
import com.intuit.fxrates.persistence.RatesPersistence;
import com.intuit.fxrates.util.BigDecimalUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultRatesSetupStrategy implements RatesSetupStrategy {

    @Autowired
    private RatesPersistence persistence;

    @Override
    public void persistRates(FXRatesDTO dto) {
        persistence.clearPersistence();

        log.info("Setting up rates data with base currency:: " + dto.getBase());
        persistence.setExchangeRates(dto.getBase(), dto.getRates());
        persistence.setExchangeRate(dto.getBase(), dto.getBase(), BigDecimal.ONE.stripTrailingZeros());

        // Setting rate among each supported currency
        persistInterSupportRatesFromBase(dto);
    }

    private void persistInterSupportRatesFromBase(FXRatesDTO dto) {
        final List<String> currencies = new ArrayList<>(dto.getRates().keySet());
        for (int i = 0; i < currencies.size(); i++) {
            for (int j = i + 1; j < currencies.size(); j++) {
                final String currency1 = currencies.get(i);
                final String currency2 = currencies.get(j);
                final BigDecimal baseToCurrency1 = dto.getRates().get(currency1);
                final BigDecimal baseToCurrency2 = dto.getRates().get(currency2);
                final BigDecimal currency1ToBase = BigDecimalUtil.getReciprocal(baseToCurrency1);
                persistence.setExchangeRate(currency1, currency2, currency1ToBase.multiply(baseToCurrency2));
            }
        }
    }

}
