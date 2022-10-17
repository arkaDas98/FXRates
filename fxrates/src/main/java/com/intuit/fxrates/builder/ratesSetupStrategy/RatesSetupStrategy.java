package com.intuit.fxrates.builder.ratesSetupStrategy;

import com.intuit.fxrates.dto.FXRatesDTO;

public interface RatesSetupStrategy {

    void persistRates(FXRatesDTO baseData);
}
