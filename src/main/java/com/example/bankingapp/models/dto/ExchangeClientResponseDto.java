package com.example.bankingapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeClientResponseDto {
    private String symbol;
    private BigDecimal rate;
    private String timestamp;
}
