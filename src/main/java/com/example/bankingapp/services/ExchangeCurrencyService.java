package com.example.bankingapp.services;

import com.example.bankingapp.models.entity.ExchangeCurrency;

public interface ExchangeCurrencyService {

    ExchangeCurrency getExchangeRate(String currency);

    void saveKZTtoUSDExchangeRate();

    void saveRUBtoUSDExchangeRate();
}
