package com.standardbank.currencyconversionservice.core;

import java.security.InvalidParameterException;
import java.util.Optional;

import static java.lang.String.format;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 14:30
 *
 */
public interface ExchangeRateRepository {

    Optional<ExchangeRate> findByBaseCurrency(String baseCurrency);

    default ExchangeRate getBaseCurrencyRates(String baseCurrency) {
        return findByBaseCurrency(baseCurrency)
            .orElseThrow(() -> new InvalidParameterException(format("Base currency %s could not be found.", baseCurrency)));
    }

    void deleteAll();

    ExchangeRate save(ExchangeRate exchangeRate);

}
