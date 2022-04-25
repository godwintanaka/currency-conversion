package com.standardbank.currencyconversionservice.api;

/**
 * @author Godwin Tavirimirwa
 * created at 24/4/2022 06:09
 *
 * <p>Responsible for currency conversions</p>
 */
public interface CurrencyConvertor {
    String convert(String fromCurrency, String toCurrency, Double amount);
}
