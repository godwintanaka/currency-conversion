package com.standardbank.currencyconversionservice.infra.api.rest;

import com.standardbank.currencyconversionservice.api.ExchangeRateFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 14:02
 *
 * <p>Exchange rate end-points</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/exchange-rate")
@Tag(name = "Exchange Rate Resource")
public class ExchangeRateResource {

    private final ExchangeRateFactory exchangeRateFactory;

    /**
     * Removes existing rates for different base currencies and replaces with fresh ones
     *
     * @return result for refreshing exchange rates
     */
    @Operation(summary = "Refreshes exchange rates.", description = "It refreshes for USD, CAD, EUR, GPB and AUD." +
        " These are also the only currencies configured for the sake of this service")
    @PostMapping
    public ResponseEntity<?> create() {
        return ResponseEntity.ok(exchangeRateFactory.generate());
    }

}
