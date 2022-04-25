package com.standardbank.currencyconversionservice.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Godwin Tavirimirwa
 * created at 23/4/2022 13:39
 */
@Data
@Builder
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Entity
@Table(name = "currency_exchange_rate")
public class ExchangeRate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 20, nullable = false)
    private Long id;

    @Setter(AccessLevel.PRIVATE)
    @Column(name = "base_currency", nullable = false)
    private String baseCurrency;

    @Setter(AccessLevel.PRIVATE)
    @Column(name = "rates", columnDefinition = "LONGTEXT", nullable = false)
    private String rates;

    @Setter(AccessLevel.PRIVATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
