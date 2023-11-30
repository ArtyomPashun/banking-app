package com.example.bankingapp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Exchange Currency Dto")
public class ExchangeCurrencyDto {
    private String currencyCode;
    private BigDecimal rate;
    private LocalDate date;
}
