package com.example.bankingapp.models.mapStruct;

import com.example.bankingapp.models.dto.ExchangeClientResponseDto;
import com.example.bankingapp.models.dto.ExchangeCurrencyDto;
import com.example.bankingapp.models.entity.ExchangeCurrency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExchangeCurrencyMapper {

    ExchangeCurrencyDto toExchangeCurrencyDto (ExchangeCurrency exchangeCurrency);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    ExchangeCurrency toExchangeCurrency (ExchangeCurrencyDto exchangeCurrencyDto);

    @Mapping(target = "currencyCode", source = "symbol")
    @Mapping(target = "date", ignore = true)
    ExchangeCurrencyDto toExchangeCurrencyDto (ExchangeClientResponseDto exchangeClientResponseDto);
}
