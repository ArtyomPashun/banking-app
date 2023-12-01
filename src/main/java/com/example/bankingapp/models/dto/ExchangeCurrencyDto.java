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
    @Schema(name = "currencyCode", example = "USD/KZT")
    private String currencyCode;
    @Schema(name = "rate", example = "460.00000")
    private BigDecimal rate;
    @Schema(name = "date", example = "2023-11-30")
    private LocalDate date;
}
