package com.example.bankingapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeCurrencyDto {
    private String currencyCode;
    private BigDecimal rate;
    private LocalDate date;
}
