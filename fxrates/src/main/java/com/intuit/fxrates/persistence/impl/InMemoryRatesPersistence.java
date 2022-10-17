package com.intuit.fxrates.persistence.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.intuit.fxrates.persistence.RatesPersistence;
import com.intuit.fxrates.util.BigDecimalUtil;

import org.springframework.stereotype.Repository;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class InMemoryRatesPersistence implements RatesPersistence {

    private final Map<String, Map<String, BigDecimal>> rates;

    public InMemoryRatesPersistence() {
        this.rates = new HashMap<>();
    }

    @Override
    public BigDecimal getExchangeRate(@NonNull final String fromCurrency, @NonNull final String toCurrency) {
        return rates.get(fromCurrency).get(toCurrency);
    }

    @Override
    public void setExchangeRate(@NonNull final String fromCurrency, @NonNull final String toCurrency,
            @NonNull final BigDecimal rate) {
        if (!rates.containsKey(fromCurrency))
            rates.put(fromCurrency, new HashMap<>());
        rates.get(fromCurrency).put(toCurrency, rate);

        if (!rates.containsKey(toCurrency))
            rates.put(toCurrency, new HashMap<>());
        rates.get(toCurrency).put(fromCurrency, BigDecimalUtil.getReciprocal(rate));
    }

    @Override
    public void setExchangeRates(String base, Map<String, BigDecimal> rate) {
        rates.put(base, rate);
        persistSupportToBaseRates(base, rate);
    }

    private void persistSupportToBaseRates(String base, Map<String, BigDecimal> rate) {
        rate.entrySet().forEach(entry -> {
            log.info("Setting up reverse rate from currency:: " + entry.getKey() + " to base:" + base);
            setExchangeRate(entry.getKey(), entry.getKey(), BigDecimal.ONE);
            setExchangeRate(entry.getKey(), base, BigDecimalUtil.getReciprocal(entry.getValue()));
        });
    }

    @Override
    public Set<String> getSupportedCurrencies() {
        return rates.keySet();
    }

    @Override
    public void clearPersistence() {
        rates.clear();
    }

}
