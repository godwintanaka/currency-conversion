package com.standardbank.currencyconversionservice.infra.jpa;

import com.standardbank.currencyconversionservice.core.ExchangeRate;
import com.standardbank.currencyconversionservice.core.ExchangeRateRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 14:01
 */
public interface ExchangeRateJpaRepository extends JpaRepository<ExchangeRate, Long>, ExchangeRateRepository {
    Optional<ExchangeRate> findByBaseCurrency(String baseCurrency);
}
