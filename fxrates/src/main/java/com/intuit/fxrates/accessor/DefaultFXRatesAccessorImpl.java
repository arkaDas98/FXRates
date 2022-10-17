package com.intuit.fxrates.accessor;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.fxrates.dto.FXRatesDTO;
import com.intuit.fxrates.exceptions.ApiAccessException;
import com.intuit.fxrates.exceptions.JacksonException;
import com.intuit.fxrates.util.HttpAPIUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultFXRatesAccessorImpl implements FXRatesAccessor {

    @Autowired
    private ObjectMapper objectMapper;

    private final String baseUrl = "https://openexchangerates.org/api/latest.json?app_id=9d685b96790e46ff80d21b2589261bdc";

    @Override
    public FXRatesDTO fetchLatestRates(String baseCurrency) {
        final String urlString = baseUrl + getBaseCurrencyParameter(baseCurrency);
        return getRatesRESTCall(urlString);
    }

    @Override
    public FXRatesDTO fetchLatestRates(String baseCurrency, List<String> conversions) {
        final String urlString = baseUrl + getBaseCurrencyParameter(baseCurrency) + getSymbolsParameter(conversions);
        return getRatesRESTCall(urlString);
    }

    private FXRatesDTO getRatesRESTCall(final String urlString) {
        final String result;
        try {
            result = HttpAPIUtil.getStringResponse(urlString);
        } catch (Exception e) {
            log.error("Error in fetching default rates from external API", e);
            throw new ApiAccessException("Error in fetching default rates from external API", e);
        }

        final FXRatesDTO fxRatesDTO;
        try {
            fxRatesDTO = objectMapper.readValue(result, FXRatesDTO.class);
        } catch (Exception e) {
            log.error("Error in deserializing response from api", e);
            throw new JacksonException("Error in deserializing response from api", e);
        }
        return fxRatesDTO;
    }

    private String getBaseCurrencyParameter(String base) {
        StringBuilder param = new StringBuilder("&base=");
        param.append(base);
        return param.toString();
    }

    private String getSymbolsParameter(List<String> conversions) {
        StringBuilder param = new StringBuilder("&symbols=");
        conversions.stream().forEach(conversion -> param.append("," + conversion));
        return param.toString();
    }
}
