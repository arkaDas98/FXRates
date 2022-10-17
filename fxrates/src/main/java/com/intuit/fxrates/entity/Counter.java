package com.intuit.fxrates.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Counter {

    private final String pair;
    private final int count;
}
