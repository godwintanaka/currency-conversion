package com.standardbank.currencyconversionservice.infra.api.rest;

import com.standardbank.currencyconversionservice.api.ExchangeRateGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 18:29
 *
 * <p>It inserts exchange rates in the database on application start. This was put to lesson the burden of first having to load the data</p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateInitializer implements CommandLineRunner {

    final ExchangeRateGenerator exchangeRateGenerator;

    @Override
    public void run(String... args) {
        log.info("Inserting exchange rates.....{}", exchangeRateGenerator.generate());
    }
}
