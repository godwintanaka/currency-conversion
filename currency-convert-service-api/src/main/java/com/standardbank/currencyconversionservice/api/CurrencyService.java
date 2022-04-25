package com.standardbank.currencyconversionservice.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.standardbank.currencyconversionservice.core.Currency;
import com.standardbank.currencyconversionservice.core.ExchangeRate;
import com.standardbank.currencyconversionservice.core.ExchangeRateDto;
import com.standardbank.currencyconversionservice.core.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Godwin Tavirimirwa
 * created at 24/4/2022 06:14
 *
 * <p>Business logic for currencies</p>
 */
@Service
@RequiredArgsConstructor
public class CurrencyService implements CurrencyManager {

    final ExchangeRateRepository exchangeRateRepository;

    /**
     * Extracts all exchange rates base on a given base currency
     *
     * @param baseCurrency to which the exchange rates are needed
     * @return exchange rates for the base currency
     */
    public ExchangeRateDto getByBaseCurrency(String baseCurrency) {
        ExchangeRate exchangeRate = exchangeRateRepository.getBaseCurrencyRates(requireNonNull(baseCurrency, "Base currency is required."));
        List<Currency> exchangeRates;
        try {
            exchangeRates = new ObjectMapper().readValue(exchangeRate.getRates(), new TypeReference<List<Currency>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Could not read rates.");
        }
        return ExchangeRateDto.of(baseCurrency.toUpperCase(), exchangeRates);
    }
}
