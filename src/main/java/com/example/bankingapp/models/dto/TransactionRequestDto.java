package com.example.bankingapp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Transaction Request Dto")
public class TransactionRequestDto {
    private BigInteger accountFrom;
    private BigInteger accountTo;
    private String currency;
    private BigDecimal sum;
    private String expenseCategory;
}
