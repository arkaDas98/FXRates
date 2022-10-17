package com.intuit.fxrates.component;

import java.util.ArrayList;
import java.util.List;

import com.intuit.fxrates.builder.ExchangesBuilder;
import com.intuit.fxrates.builder.RatesSetupBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RatesSetupComponent {

    @Autowired
    private RatesSetupBuilder ratesBuilder;
    @Autowired
    private ExchangesBuilder exchangesBuilder;

    public void setDefaultRates(final String base) {
        log.info("Setting up default rates with base : " + base);
        ratesBuilder.setupDefaultRates(base);
    }

    public void setDefaultRates(final String base, List<String> currencies) {
        log.info("Setting up default rates with base + " + base + "  & supported: " + currencies.toString());
        ratesBuilder.setupDefaultRates(base, currencies);
    }

    public List<String> getSupportedCurrencies() {
        return new ArrayList<>(exchangesBuilder.getSupportedCurrencies());
    }
}
