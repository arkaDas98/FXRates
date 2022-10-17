package com.intuit.fxrates.accessor;

import java.util.List;

import com.intuit.fxrates.dto.FXRatesDTO;

public interface FXRatesAccessor {

    FXRatesDTO fetchLatestRates(final String baseCurrency);

    FXRatesDTO fetchLatestRates(final String baseCurrency, List<String> conversions);
}
