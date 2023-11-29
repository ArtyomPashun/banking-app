package com.example.bankingapp.services.impl;

import com.example.bankingapp.exceptions.EntityNotFoundException;
import com.example.bankingapp.models.dto.ExchangeClientResponseDto;
import com.example.bankingapp.models.dto.ExchangeCurrencyDto;
import com.example.bankingapp.models.entity.ExchangeCurrency;
import com.example.bankingapp.models.mapStruct.ExchangeCurrencyMapper;
import com.example.bankingapp.repositories.ExchangeCurrencyRepository;
import com.example.bankingapp.services.ExchangeCurrencyService;
import com.example.bankingapp.util.ExchangeClientUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeCurrencyServiceImpl implements ExchangeCurrencyService {

    private String KZT_TO_USD = "USD/KZT";

    private String RUB_TO_USD = "USD/RUB";

    @Value("${exchange.client.apikey}")
    private String API_KEY;

    private final ExchangeCurrencyRepository exchangeCurrencyRepository;

    private final ExchangeClientUtil exchangeClientUtil;

    private final ExchangeCurrencyMapper exchangeCurrencyMapper;

    @Override
    @Transactional(readOnly = true)
    public ExchangeCurrency getExchangeRate(String currency) {
        return exchangeCurrencyRepository.findAllByCurrencyCodeEquals(currency)
                .stream()
                .min((currency1, currency2) -> currency2.getId().compareTo(currency1.getId()))
                .orElseThrow(() -> new EntityNotFoundException("There is no such currency pair"));
    }


    @Scheduled(cron = "0 5 0 * * ?")
    @Override
    @Transactional
    public void saveKZTtoUSDExchangeRate() {
        ExchangeClientResponseDto kzTtoUSDExchangeRate = exchangeClientUtil.getExchangeRate(KZT_TO_USD, API_KEY);
        ExchangeCurrencyDto exchangeCurrencyDto = exchangeCurrencyMapper.toExchangeCurrencyDto(kzTtoUSDExchangeRate);
        ExchangeCurrency exchangeCurrency = exchangeCurrencyMapper.toExchangeCurrency(exchangeCurrencyDto);
        exchangeCurrency.setDate(LocalDate.now());
        exchangeCurrencyRepository.save(exchangeCurrency);
    }

    @Scheduled(cron = "0 6 0 * * ?")
    @Override
    @Transactional
    public void saveRUBtoUSDExchangeRate() {
        ExchangeClientResponseDto ruBtoUSDExchangeRate = exchangeClientUtil.getExchangeRate(RUB_TO_USD, API_KEY);
        ExchangeCurrencyDto exchangeCurrencyDto = exchangeCurrencyMapper.toExchangeCurrencyDto(ruBtoUSDExchangeRate);
        ExchangeCurrency exchangeCurrency = exchangeCurrencyMapper.toExchangeCurrency(exchangeCurrencyDto);
        exchangeCurrency.setDate(LocalDate.now());
        exchangeCurrencyRepository.save(exchangeCurrency);
    }
}
