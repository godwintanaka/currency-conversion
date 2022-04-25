package com.standardbank.currencyconversionservice.infra.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.standardbank.currencyconversionservice.core.Currency;
import com.standardbank.currencyconversionservice.core.ExchangeRate;
import com.standardbank.currencyconversionservice.core.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 22:15
 */
@ActiveProfiles("junit")
@DataJpaTest
class ExchangeRateJpaRepositoryTest {

    @Autowired
    ExchangeRateRepository exchangeRateRepository;

    /**
     * Saving record fails if required field baseCurrency is not given
     *
     * @throws JsonProcessingException serializing exception
     */
    @Test
    void fail_save_no_base_currency() throws JsonProcessingException {
        Currency currency = Currency.builder().code("AUD").name("Australian Dollar").rate(1.2345).inverseRate(0.1234).build();
        ExchangeRate exchangeRate = ExchangeRate.builder().rates(new ObjectMapper().writeValueAsString(currency)).createdDate(LocalDateTime.now()).build();
        assertThrows(DataIntegrityViolationException.class, () -> exchangeRateRepository.save(exchangeRate));
    }

    /**
     * Saving record fails if exchange rates are missing
     */
    @Test
    void fail_save_missing_currency_code() {
        ExchangeRate exchangeRate = ExchangeRate.builder().baseCurrency("USD").createdDate(LocalDateTime.now()).build();
        assertThrows(DataIntegrityViolationException.class, () -> exchangeRateRepository.save(exchangeRate));
    }

    /**
     * Saving record fails if created date is not populated
     */
    @Test
    void fail_save_create_date_missing() throws JsonProcessingException {
        Currency currency = Currency.builder().code("AUD").name("Australian Dollar").rate(1.2345).inverseRate(0.1234).build();
        ExchangeRate exchangeRate = ExchangeRate.builder().baseCurrency("USD").rates(new ObjectMapper().writeValueAsString(currency)).build();
        assertThrows(DataIntegrityViolationException.class, () -> exchangeRateRepository.save(exchangeRate));
    }

    /**
     * If base currency provided is not found, an Exception is thrown
     *
     * @throws JsonProcessingException serializing exception
     */
    @Test
    void fail_find_by_base_currency() throws JsonProcessingException {
        Currency currency = Currency.builder().code("AUD").name("Australian Dollar").rate(1.2345).inverseRate(0.1234).build();
        ExchangeRate exchangeRate = ExchangeRate.builder().baseCurrency("USD").rates(new ObjectMapper().writeValueAsString(currency)).createdDate(LocalDateTime.now()).build();
        ExchangeRate savedRate = exchangeRateRepository.save(exchangeRate);
        assertNotNull(savedRate);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> exchangeRateRepository.getBaseCurrencyRates("ZAR"));
    }

    /**
     * Success test of saving an exchange rate
     *
     * @throws JsonProcessingException serializing exception
     */
    @Test
    void test_success_save() throws JsonProcessingException {
        Currency currency = Currency.builder().code("AUD").name("Australian Dollar").rate(1.2345).inverseRate(0.1234).build();
        ExchangeRate exchangeRate = ExchangeRate.builder().baseCurrency("USD").rates(new ObjectMapper().writeValueAsString(currency)).createdDate(LocalDateTime.now()).build();
        ExchangeRate savedRate = exchangeRateRepository.save(exchangeRate);
        assertNotNull(savedRate);
        assertTrue(savedRate.getId() > 0);
    }

    /**
     * Success test of saving an exchange rate
     *
     * @throws JsonProcessingException serializing exception
     */
    @Test
    void test_success_find_by_base_currency() throws JsonProcessingException {
        Currency currency = Currency.builder().code("AUD").name("Australian Dollar").rate(1.2345).inverseRate(0.1234).build();
        ExchangeRate exchangeRate = ExchangeRate.builder().baseCurrency("USD").rates(new ObjectMapper().writeValueAsString(currency)).createdDate(LocalDateTime.now()).build();
        ExchangeRate savedRate = exchangeRateRepository.save(exchangeRate);
        assertNotNull(savedRate);
        assertTrue(exchangeRateRepository.findByBaseCurrency("USD").isPresent());
    }

}