package com.standardbank.currencyconversionservice.api;

import com.standardbank.currencyconversionservice.core.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 14:07
 *
 * <p>Responsible for fetching and refreshing already saved exchange rates</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateGenerator implements ExchangeRateFactory {

    final ExchangeRateRepository exchangeRateRepository;

    /**
     * Refreshes exchanges rates in the database
     *
     * @return result for persisting exchange rates to the database
     */
    public String generate() {
        exchangeRateRepository.deleteAll();
        getExchangeRates().forEach(exchangeRateRepository::save);
        return "Exchange rates saved successfully.";
    }

}
