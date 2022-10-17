package com.intuit.fxrates.persistence;

import java.util.List;

import com.intuit.fxrates.entity.Counter;

public interface CounterPersistence {

    public void incrementCounter(String fromCurrency, String toCurrency);

    public List<Counter> getTopPairs(int topValue);
}
