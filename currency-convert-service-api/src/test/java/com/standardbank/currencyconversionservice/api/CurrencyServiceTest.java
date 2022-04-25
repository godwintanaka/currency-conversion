package com.standardbank.currencyconversionservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.standardbank.currencyconversionservice.core.Currency;
import com.standardbank.currencyconversionservice.core.ExchangeRate;
import com.standardbank.currencyconversionservice.core.ExchangeRateDto;
import com.standardbank.currencyconversionservice.core.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Godwin Tavirimirwa
 * created at 24/4/2022 06:22
 *
 * <p>Currency relates test cases</p>
 */
@SpringBootTest
class CurrencyServiceTest {

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    private CurrencyManager currencyService;

    @BeforeEach
    void setUp() {
        when(exchangeRateRepository.save(any(ExchangeRate.class))).thenReturn(completeExchangeRate());
        currencyService = new CurrencyService(exchangeRateRepository);
    }

    /**
     * If a non existing base currency is given, a Runtime exception is thrown
     */
    @Test
    void fail_get_by_base_currency() {
        assertThrows(RuntimeException.class, () -> currencyService.getByBaseCurrency("ZAR"));
    }

    /**
     * Success test for searching with an existing base currency
     */
    @Test
    void test_success_get_by_base_currency() {
        when(exchangeRateRepository.getBaseCurrencyRates("USD")).thenReturn(completeExchangeRate());
        ExchangeRateDto exchangeRateDto = currencyService.getByBaseCurrency("USD");
        assertNotNull(exchangeRateDto);
        assertTrue(exchangeRateDto.getRates().stream().anyMatch(currency -> currency.getInverseRate().equals(0.73089284623505)));
    }

    /**
     * Contains baseCurrency cross rates
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