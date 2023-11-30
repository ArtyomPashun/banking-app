package com.example.bankingapp.controllers;

import com.example.bankingapp.services.ExchangeCurrencyService;
import com.example.bankingapp.models.mapStruct.ExchangeCurrencyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExchangeCurrencyService exchangeCurrencyService;

    @Mock
    private ExchangeCurrencyMapper exchangeCurrencyMapper;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(exchangeRateController).build();
    }

    @Test
    void testGetAllTransactionsWhenInvalidCurrencyThenReturnBadRequest() throws Exception {
        when(exchangeCurrencyService.getExchangeRate(anyString())).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency code"));

        mockMvc.perform(get("/api/v1/exchange")
                        .param("currency", "INVALID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}