package com.standardbank.currencyconversionservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.standardbank.currencyconversionservice.core.Currency;
import com.standardbank.currencyconversionservice.core.ExchangeRate;
import com.standardbank.currencyconversionservice.core.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 19:41
 *
 * <p>
 * Tests to do with exchange rates
 * </p>
 */
@SpringBootTest
class ExchangeRateGeneratorTest {

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    private ExchangeRateFactory exchangeRateService;

    @BeforeEach
    void setUp() {
        when(exchangeRateRepository.save(any(ExchangeRate.class))).thenReturn(completeExchangeRate());
        exchangeRateService = new ExchangeRateGenerator(exchangeRateRepository);
    }

    /**
     * Success test for saving exchange rates in that database
     */
    @Test
    void test_success_save_exchange_rate() {
        String response = exchangeRateService.generate();
        assertNotNull(exchangeRateService);
        assertEquals("Exchange rates saved successfully.", response);
    }

    /**
     * A complete exchange rate object
     *
     * @return exchange rate object
     */
    private ExchangeRate completeExchangeRate() {
        try {
            List<Currency> usdCrossRates = Arrays.asList(Currency.builder().code("EUR").name("Euro").rate(0.9225002475396).inverseRate(1.0840105492297).build(),
                Currency.builder().code("CAD").name("Canadian Dollar").rate(1.2650748924554).inverseRate(0.79046703555952).build(),
                Currency.builder().code("AUD").name("Australian Dollar").rate(1.2650748924554).inverseRate(0.73089284623505).build(),
                Currency.builder().code("GBP").name("U.K. Pound Sterling").rate(1.2650748924554).inverseRate(1.2943953653188).build());
            return ExchangeRate.builder().baseCurrency("USD").createdDate(now()).rates(new ObjectMapper().writeValueAsString(usdCrossRates)).build();
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Could not serialize object");
        }
    }

}