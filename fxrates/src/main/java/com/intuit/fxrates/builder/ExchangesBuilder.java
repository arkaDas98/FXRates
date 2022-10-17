package com.intuit.fxrates.builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.intuit.fxrates.entity.Counter;
import com.intuit.fxrates.persistence.CounterPersistence;
import com.intuit.fxrates.persistence.RatesPersistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExchangesBuilder {

    @Autowired
    private RatesPersistence ratesPersistence;
    @Autowired
    private CounterPersistence counterPersistence;

    public BigDecimal getExchangeRate(@NonNull final String fromCurrency, @NonNull final String toCurrency) {
        counterPersistence.incrementCounter(fromCurrency, toCurrency);
        log.info("Incremented counter for conversion from: " + fromCurrency + " to :" + toCurrency);
        return ratesPersistence.getExchangeRate(fromCurrency, toCurrency);
    }

    public List<Counter> getTopRequestedPairs(int topVal) {
        return counterPersistence.getTopPairs(topVal);
    }

    public Set<String> getSupportedCurrencies() {
        return ratesPersistence.getSupportedCurrencies();
    }
}
