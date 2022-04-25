package com.standardbank.currencyconversionservice.infra.api.rest;

import com.standardbank.currencyconversionservice.api.CurrencyConvertorService;
import com.standardbank.currencyconversionservice.api.CurrencyManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 17:11
 *
 * <p>End-points for currencies</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/currency")
@Tag(name = "Currency Resource")
public class CurrencyResource {

    final CurrencyConvertorService currencyConvertorService;
    final CurrencyManager currencyManager;

    /**
     * Converts amount given from one currency to another
     *
     * @param fromCurrency base currency
     * @param toCurrency   target currency
     * @param amount       value to be converted
     * @return response after currency conversion, i.e the value in target currency
     */
    @Operation(summary = "Converts one currency to another.", description = "On this service only 5 currencies were configured, i.e USD, CAD, EUR, GPB, AUD.")
    @GetMapping(value = "/convert", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> convert(@RequestParam String fromCurrency, @RequestParam String toCurrency, @RequestParam Double amount) {
        return ResponseEntity.ok(currencyConvertorService.convert(fromCurrency, toCurrency, amount));
    }

    /**
     * Gets cross rates for the given base currency
     *
     * @param baseCurrency which cross rates are required
     * @return list of cross rates for different currencies
     */
    @Operation(summary = "Get exchange rates for the provided base currency", description = "It presents the respective cross rates both rate and inverse rates for the given base currency.")
    @GetMapping(value = "/base-currency", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByBaseCurrency(@RequestParam String baseCurrency) {
        return ResponseEntity.ok(currencyManager.getByBaseCurrency(baseCurrency));
    }
}
