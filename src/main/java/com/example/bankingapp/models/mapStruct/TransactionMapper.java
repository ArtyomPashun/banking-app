package com.example.bankingapp.models.mapStruct;

import com.example.bankingapp.models.dto.TransactionRequestDto;
import com.example.bankingapp.models.dto.TransactionResponseDto;
import com.example.bankingapp.models.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "isLimitExceed", ignore = true)
    @Mapping(target = "account.accountNumber", source = "accountFrom")
    @Mapping(target = "expenseCategory.name", source = "expenseCategory")
    Transaction toTransaction(TransactionRequestDto transactionRequestDto);

    @Mapping(target = "accountFrom", source = "account.accountNumber")
    @Mapping(target = "expenseCategory", source ="expenseCategory.name")
    @Mapping(target = "limit", source ="account.monthlyLimit")
    @Mapping(target = "limitDate", source ="account.limitSettingDate")
    @Mapping(target = "limitCurrency", source ="account.currencyLimit")
    TransactionResponseDto toTransactionResponseDto(Transaction transaction);

    List<TransactionResponseDto> toListTransactionResponseDto (List<Transaction> list);
}
