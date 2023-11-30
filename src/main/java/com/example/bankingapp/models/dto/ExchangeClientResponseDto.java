package com.example.bankingapp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Exchange Client Response Dto")
public class ExchangeClientResponseDto {
    private String symbol;
    private BigDecimal rate;
    private String timestamp;
}
