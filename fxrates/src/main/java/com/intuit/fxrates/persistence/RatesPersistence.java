package com.intuit.fxrates.persistence;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface RatesPersistence {

    public BigDecimal getExchangeRate(final String fromCurrency, final String toCurrency);

    public void setExchangeRate(final String fromCurrency, final String toCurrency, BigDecimal rate);

    public void setExchangeRates(final String fromCurrency, final Map<String, BigDecimal> rate);

    public Set<String> getSupportedCurrencies();

    public void clearPersistence();
}
