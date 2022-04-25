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

import static java.lang.Double.parseDouble;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Godwin Tavirimirwa
 * created at 24/4/2022 06:44
 *
 * <p>Currency conversion related tests</p>
 */
@SpringBootTest
class CurrencyConvertorServiceTest {

    @MockBean
    private ExchangeRateRepository exchangeRateRepository;

    private CurrencyConvertor convertorService;

    @BeforeEach
    void setUp() {
        when(exchangeRateRepository.getBaseCurrencyRates("USD")).thenReturn(completeExchangeRate());
        convertorService = new CurrencyConvertorService(exchangeRateRepository);
    }

    /**
     * When an invalid fromCurrency is given, conversion fails
     */
    @Test
    void fail_convert_invalid_from_currency_given() {
        assertThrows(RuntimeException.class, () -> convertorService.convert("BB", "AUD", 1000.0));
    }

    /**
     * If an invalid toCurrency is given, conversion fails
     */
    @Test
    void fail_convert_invalid_to_currency_given() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> convertorService.convert("USD", "XX", 999.23));
        assertEquals("Currency conversion failed.", exception.getMessage());
    }

    /**
     * Success case of converting a currency
     */
    @Test
    void test_success_currency_conversion() {
        String response = convertorService.convert("USD", "AUD", 1000.0);
        assertNotNull(response);
        assertEquals(1368.19, parseDouble(response));
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