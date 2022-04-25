package com.standardbank.currencyconversionservice.api;

import com.standardbank.currencyconversionservice.core.ExchangeRateDto;

/**
 * @author Godwin Tavirimirwa
 * created at 24/4/2022 06:12
 *
 * <p>Manages currencies</p>
 */
public interface CurrencyManager {
    ExchangeRateDto getByBaseCurrency(String baseCurrency);
}
