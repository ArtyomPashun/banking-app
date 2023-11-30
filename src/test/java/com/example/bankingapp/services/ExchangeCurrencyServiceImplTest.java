package com.example.bankingapp.services;

import com.example.bankingapp.exceptions.EntityNotFoundException;
import com.example.bankingapp.models.entity.ExchangeCurrency;

import com.example.bankingapp.repositories.ExchangeCurrencyRepository;
import com.example.bankingapp.services.impl.ExchangeCurrencyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExchangeCurrencyServiceImplTest {

    @Mock
    private ExchangeCurrencyRepository exchangeCurrencyRepository;

    @InjectMocks
    private ExchangeCurrencyServiceImpl exchangeCurrencyService;

    @Test
    void testGetExchangeRateWhenCurrencyExistsThenReturnExchangeRate() {
        String currencyCode = "USD";
        ExchangeCurrency exchangeCurrency = new ExchangeCurrency(1, currencyCode, BigDecimal.valueOf(1.0), LocalDate.now());
        when(exchangeCurrencyRepository.findAllByCurrencyCodeEquals(currencyCode)).thenReturn(Collections.singletonList(exchangeCurrency));

        ExchangeCurrency result = exchangeCurrencyService.getExchangeRate(currencyCode);

        assertNotNull(result);
        assertEquals(exchangeCurrency, result);
    }

    @Test
    void testGetExchangeRateWhenCurrencyDoesNotExistThenThrowEntityNotFoundException() {
        String currencyCode = "EUR";
        when(exchangeCurrencyRepository.findAllByCurrencyCodeEquals(currencyCode)).thenReturn(Collections.emptyList());

        assertThrows(EntityNotFoundException.class, () -> exchangeCurrencyService.getExchangeRate(currencyCode));
    }
}