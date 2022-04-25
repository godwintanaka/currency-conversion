package com.standardbank.currencyconversionservice.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 14:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Currency {
    private String code;
    private String name;
    private Double rate;
    private Double inverseRate;
}
