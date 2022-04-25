package com.standardbank.currencyconversionservice.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.standardbank.currencyconversionservice.core.Currency;
import com.standardbank.currencyconversionservice.core.ExchangeRate;
import com.standardbank.currencyconversionservice.core.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 16:53
 *
 * <p>Business logic for currency conversion</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConvertorService implements CurrencyConvertor {

    final ExchangeRateRepository exchangeRateRepository;

    /**
     * Converts amount given for a currency to another
     *
     * @param fromCurrency base currency
     * @param toCurrency   target currency
     * @param amount       value to be converted
     * @return amount of target currency
     */
    public String convert(String fromCurrency, String toCurrency, Double amount) {
        ExchangeRate fromCurrencyRates = exchangeRateRepository.getBaseCurrencyRates(fromCurrency);
        try {
            List<Currency> exchangeRates = new ObjectMapper().readValue(fromCurrencyRates.getRates(), new TypeReference<List<Currency>>() {
            });
            Currency targetCurrency = exchangeRates.stream().filter(currency -> currency.getCode().equalsIgnoreCase(toCurrency)).findAny().orElseThrow(
                () -> new RuntimeException(String.format("No exchange rate found from %s to %s", fromCurrency, toCurrency)));
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            decimalFormat.setRoundingMode(RoundingMode.CEILING);
            double result = amount / targetCurrency.getInverseRate();
            return decimalFormat.format(result);
        } catch (Exception exception) {
            throw new RuntimeException("Currency conversion failed.");
        }
    }

}
