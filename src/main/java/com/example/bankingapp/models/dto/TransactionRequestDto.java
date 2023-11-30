package com.example.bankingapp.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {
    private BigInteger accountFrom;
    private BigInteger accountTo;
    private String currency;
    private BigDecimal sum;
    private String expenseCategory;
}
