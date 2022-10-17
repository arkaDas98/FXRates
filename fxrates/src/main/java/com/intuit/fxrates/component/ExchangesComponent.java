package com.intuit.fxrates.component;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.intuit.fxrates.builder.ExchangesBuilder;
import com.intuit.fxrates.entity.Conversions;
import com.intuit.fxrates.entity.Counter;
import com.intuit.fxrates.exceptions.UnsupportedCurrencyException;
import com.intuit.fxrates.util.BigDecimalUtil;
import com.intuit.fxrates.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExchangesComponent {
    private int CODE_SIZE = 3;

    @Autowired
    private ExchangesBuilder exchangesBuilder;

    public List<Conversions> getAllConversions(@NonNull final String conversionCommands) {
        validateCommand(conversionCommands);
        final List<Conversions> result = new LinkedList<>();
        final String[] commands = conversionCommands.split(",");
        for (String command : commands) {
            log.info("Executing process for " + command);
            result.add(getConversionsForSingleBase(command));
        }
        return result;
    }

    private Conversions getConversionsForSingleBase(final String command) {
        final String allCurrencies = StringUtils.currenciesFromCommand(command);
        final List<String> currencies = StringUtils.splitCurrencies(allCurrencies, CODE_SIZE);
        final String baseCurrency = currencies.get(0);
        final BigDecimal baseValue = BigDecimalUtil.valueFromCommand(command);
        final Conversions newConversions = new Conversions(baseCurrency, baseValue);

        for (int i = 1; i < currencies.size(); i++) {
            newConversions.addCurrencyUsingExchangeRate(currencies.get(i),
                    exchangesBuilder.getExchangeRate(baseCurrency, currencies.get(i)));
        }
        return newConversions;
    }

    private void validateCommand(final String conversionCommands) {
        final String allCurrenciesInRequest = StringUtils.currenciesFromCommand(conversionCommands);
        final List<String> currencies = StringUtils.splitCurrencies(allCurrenciesInRequest, CODE_SIZE);
        if (!exchangesBuilder.getSupportedCurrencies().containsAll(currencies)) {
            log.error("Unsupported currency present: " + currencies);
            throw new UnsupportedCurrencyException("Unsupported currency present in request");
        }
    }

    public List<Counter> getTopMostRequestedPairs(int topVal) {
        return exchangesBuilder.getTopRequestedPairs(topVal);
    }
}
