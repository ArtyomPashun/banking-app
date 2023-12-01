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
    @Schema(name = "accountFrom", example = "1111111111")
    private BigInteger accountFrom;
    @Schema(name = "accountTo", example = "1010101011")
    private BigInteger accountTo;
    @Schema(name = "currency", example = "USD")
    private String currency;
    @Schema(name = "sum", example = "100")
    private BigDecimal sum;
    @Schema(name = "expenseCategory", example = "services")
    private String expenseCategory;
}
