package com.standardbank.currencyconversionservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.standardbank.currencyconversionservice.core.Currency;
import com.standardbank.currencyconversionservice.core.ExchangeRate;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.now;

/**
 * @author Godwin Tavirimirwa
 * created at 24/4/2022 06:16
 *
 * <p>
 * Deals with exchange rate related functionalities
 * </p>
 */
public interface ExchangeRateFactory {
    ObjectMapper mapper = new ObjectMapper();

    String generate();

    /**
     * Creates static exchange rates for each base currency. This is just a sample.A more optimum approach would be to read on runtime then stored in the database.
     * These rates were taken from https://www.floatrates.com/json-feeds.html
     *
     * @return list of exchange rates
     */
    default List<ExchangeRate> getExchangeRates() {
        try {
            List<Currency> usdCrossRates = Arrays.asList(Currency.builder().code("EUR").name("Euro").rate(0.9225002475396).inverseRate(1.0840105492297).build(),
                Currency.builder().code("CAD").name("Canadian Dollar").rate(1.2650748924554).inverseRate(0.79046703555952).build(),
                Currency.builder().code("AUD").name("Australian Dollar").rate(1.2650748924554).inverseRate(0.73089284623505).build(),
                Currency.builder().code("GBP").name("U.K. Pound Sterling").rate(1.2650748924554).inverseRate(1.2943953653188).build());
            ExchangeRate usdExchangeRate = ExchangeRate.builder().baseCurrency("USD").createdDate(now()).rates(mapper.writeValueAsString(usdCrossRates)).build();

            List<Currency> cadCrossRates = Arrays.asList(Currency.builder().code("EUR").name("Euro").rate(0.72920603597558).inverseRate(1.3713545289873).build(),
                Currency.builder().code("USD").name("U.S. Dollar").rate(0.79046703555952).inverseRate(1.2650748924554).build(),
                Currency.builder().code("AUD").name("Australian Dollar").rate(1.0815087870012).inverseRate(0.92463418884723).build(),
                Currency.builder().code("GBP").name("U.K. Pound Sterling").rate(0.6106843834108).inverseRate(1.6375070775755).build());
            ExchangeRate cadExchangeRate = ExchangeRate.builder().baseCurrency("CAD").createdDate(now()).rates(mapper.writeValueAsString(cadCrossRates)).build();

            List<Currency> audCrossRates = Arrays.asList(Currency.builder().code("EUR").name("Euro").rate(0.67424883157678).inverseRate(1.4831319731937).build(),
                Currency.builder().code("CAD").name("Canadian Dollar").rate(0.92463418884723).inverseRate(1.0815087870012).build(),
                Currency.builder().code("USD").name("U.S. Dollar").rate(0.730892846235054).inverseRate(1.3681896124051).build(),
                Currency.builder().code("GBP").name("U.K. Pound Sterling").rate(0.56465965949672).inverseRate(1.7709782931745).build());
            ExchangeRate audExchangeRate = ExchangeRate.builder().baseCurrency("AUD").createdDate(now()).rates(mapper.writeValueAsString(audCrossRates)).build();

            List<Currency> gbpCrossRates = Arrays.asList(Currency.builder().code("EUR").name("Euro").rate(1.1940800449208).inverseRate(0.83746479497223).build(),
                Currency.builder().code("CAD").name("Canadian Dollar").rate(1.6375070775755).inverseRate(0.6106843834108).build(),
                Currency.builder().code("AUD").name("Australian Dollar").rate(1.7709782931745).inverseRate(0.56465965949672).build(),
                Currency.builder().code("USD").name("U.S. Dollar").rate(1.2943953653188).inverseRate(0.77256148066761).build());
            ExchangeRate gpbExchangeRate = ExchangeRate.builder().baseCurrency("GBP").createdDate(now()).rates(mapper.writeValueAsString(gbpCrossRates)).build();

            List<Currency> eurCrossRates = Arrays.asList(Currency.builder().code("GBP").name("U.K. Pound Sterling").rate(0.83746479497223).inverseRate(1.1940800449208).build(),
                Currency.builder().code("CAD").name("Canadian Dollar").rate(1.3713545289873).inverseRate(0.72920603597558).build(),
                Currency.builder().code("AUD").name("Australian Dollar").rate(1.4831319731937).inverseRate(0.67424883157678).build(),
                Currency.builder().code("USD").name("U.S. Dollar").rate(1.0840105492297).inverseRate(0.92250024753963).build());
            ExchangeRate eurExchangeRate = ExchangeRate.builder().baseCurrency("EUR").createdDate(now()).rates(mapper.writeValueAsString(eurCrossRates)).build();
            return Arrays.asList(usdExchangeRate, cadExchangeRate, audExchangeRate, gpbExchangeRate, eurExchangeRate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not serialize exchange rates.");
        }
    }
}
