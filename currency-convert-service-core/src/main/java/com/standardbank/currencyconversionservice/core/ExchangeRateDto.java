package com.standardbank.currencyconversionservice.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 16:15
 */
@Data
@Builder
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ExchangeRateDto {
    private String baseCurrency;
    private List<Currency> rates;
}
