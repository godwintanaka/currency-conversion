package com.standardbank.currencyconversionservice.infra.api.rest;

import com.standardbank.currencyconversionservice.api.CurrencyConvertorService;
import com.standardbank.currencyconversionservice.api.CurrencyManager;
import com.standardbank.currencyconversionservice.core.Currency;
import com.standardbank.currencyconversionservice.core.ExchangeRateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 23:32
 */
@WebMvcTest(CurrencyResource.class)
class CurrencyResourceTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CurrencyConvertorService currencyConvertorService;

    @MockBean
    CurrencyManager currencyManager;

    /**
     * Http error code 404 is thrown if an invalid path is given
     *
     * @throws Exception generic exception
     */
    @Test
    void fail_convert_invalid_url() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("fromCurrency", "USD");
        params.add("toCurrency", "AUD");
        params.add("amount", "1000");
        when(currencyConvertorService.convert(anyString(), anyString(), anyDouble())).thenReturn("1.34566");
        mockMvc.perform(get("/currency/conversion").params(params))
            .andExpect(status().isNotFound());
    }

    /**
     * Http error code 405 is thrown if an invalid http method is provided
     *
     * @throws Exception generic exception
     */
    @Test
    void fail_convert_invalid_http_method() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("fromCurrency", "USD");
        params.add("toCurrency", "AUD");
        params.add("amount", "1000");
        when(currencyConvertorService.convert(anyString(), anyString(), anyDouble())).thenReturn("1.34566");
        mockMvc.perform(post("/currency/convert").params(params))
            .andExpect(status().isMethodNotAllowed());
    }

    /**
     * A bad request is thrown if a request lacks all parameters needed
     *
     * @throws Exception generic exception
     */
    @Test
    void fail_get_by_base_currency_invalid_parameters() throws Exception {
        when(currencyManager.getByBaseCurrency(anyString())).thenReturn(ExchangeRateDto.of("USD", exchangeRates()));
        mockMvc.perform(get("/currency/base-currency"))
            .andExpect(status().isBadRequest());
    }


    /**
     * Success test for converting a currency
     *
     * @throws Exception generic exception
     */
    @Test
    void test_success_convert() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("fromCurrency", "USD");
        params.add("toCurrency", "AUD");
        params.add("amount", "1000");
        when(currencyConvertorService.convert(anyString(), anyString(), anyDouble())).thenReturn("1.34566");
        mockMvc.perform(get("/currency/convert").params(params))
            .andExpect(status().isOk());
    }

    /**
     * Success test for getting exchange rates for a given baseCurrency
     *
     * @throws Exception generic exception
     */
    @Test
    void test_success_get_by_base_currency() throws Exception {
        when(currencyManager.getByBaseCurrency(anyString())).thenReturn(ExchangeRateDto.of("USD", exchangeRates()));
        mockMvc.perform(get("/currency/base-currency").param("baseCurrency", "USD"))
            .andExpect(status().isOk());
    }

    /**
     * Exchange rates for a baseCurrency
     *
     * @return list of exchange rates against different currencies
     */
    private List<Currency> exchangeRates() {
        return Arrays.asList(Currency.builder().code("EUR").name("Euro").rate(0.9225002475396).inverseRate(1.0840105492297).build(),
            Currency.builder().code("CAD").name("Canadian Dollar").rate(1.2650748924554).inverseRate(0.79046703555952).build(),
            Currency.builder().code("AUD").name("Australian Dollar").rate(1.2650748924554).inverseRate(0.73089284623505).build(),
            Currency.builder().code("GBP").name("U.K. Pound Sterling").rate(1.2650748924554).inverseRate(1.2943953653188).build());

    }
}