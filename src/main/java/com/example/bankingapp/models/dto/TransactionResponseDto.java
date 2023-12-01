package com.example.bankingapp.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Transaction Response Dto")
public class TransactionResponseDto {
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
    @Schema(name = "date", example = "2023-12-01T11:27:55.259281")
    private LocalDateTime date;
    @Schema(name = "limit", example = "1900.00")
    private BigDecimal limit;
    @Schema(name = "limitDate", example = "2023-12-01")
    private LocalDate limitDate;
    @Schema(name = "limitCurrency", example = "USD")
    private String limitCurrency;
}
