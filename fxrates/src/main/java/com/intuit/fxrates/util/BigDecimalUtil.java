package com.intuit.fxrates.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import lombok.NonNull;

public final class BigDecimalUtil {

    private static int SCALE = 15;

    public static BigDecimal getReciprocal(BigDecimal divisor) {
        return BigDecimal.ONE.divide(divisor, SCALE, RoundingMode.HALF_UP).stripTrailingZeros();
    }

    public static BigDecimal valueFromCommand(@NonNull final String command) {
        String value = command.replaceAll("[^0-9\\.]", "");
        if (value.isEmpty() || value.isBlank()) {
            return BigDecimal.ONE;
        }
        return BigDecimal.valueOf(Double.parseDouble(value));
    }

    public static BigDecimal multiply(BigDecimal multiplicand1, BigDecimal multiplicand2) {
        return multiplicand1.multiply(multiplicand2, new MathContext(SCALE, RoundingMode.HALF_UP)).stripTrailingZeros();
    }

}
