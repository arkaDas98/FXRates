package com.intuit.fxrates.dto;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FXRatesDTO {
    private String base;
    @Nullable
    private Long timestamp;
    private Map<String, BigDecimal> rates;

    public String toString() {
        return base.toString() + " " + timestamp.toString() + " " + rates.toString();
    }
}
