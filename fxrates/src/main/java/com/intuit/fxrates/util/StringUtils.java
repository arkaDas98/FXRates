package com.intuit.fxrates.util;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {

    public static List<String> splitCurrencies(String currencies, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < currencies.length(); i += chunkSize) {
            chunks.add(currencies.substring(i, Math.min(currencies.length(), i + chunkSize)));
        }
        return chunks;
    }

    public static String currenciesFromCommand(String command) {
        return command.replaceAll("[0-9\\.,]", "");
    }
}
