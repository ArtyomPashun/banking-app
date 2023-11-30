package com.example.bankingapp.controllers;

import com.example.bankingapp.models.dto.TransactionRequestDto;
import com.example.bankingapp.models.dto.TransactionResponseDto;
import com.example.bankingapp.models.mapStruct.TransactionMapper;
import com.example.bankingapp.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionMapper transactionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private TransactionRequestDto validTransactionRequestDto;
    private TransactionResponseDto validTransactionResponseDto;

    @BeforeEach
    void setUp() {
        validTransactionRequestDto = TransactionRequestDto.builder()
                .accountFrom(new BigInteger("123456789"))
                .accountTo(new BigInteger("987654321"))
                .currency("USD")
                .sum(new BigDecimal("100.00"))
                .expenseCategory("Groceries")
                .build();

        validTransactionResponseDto = TransactionResponseDto.builder()
                .accountFrom(new BigInteger("123456789"))
                .accountTo(new BigInteger("987654321"))
                .currency("USD")
                .sum(new BigDecimal("100.00"))
                .expenseCategory("Groceries")
                .build();
    }

    @Test
    @DisplayName("Test createNewTransaction when valid TransactionRequestDto is provided then return OK")
    void testCreateNewTransactionWhenValidTransactionRequestDtoThenReturnOk() throws Exception {
        mockMvc.perform(post("/api/v1/transactions/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validTransactionRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction was made."));
    }

    @Test
    @DisplayName("Test getAllTransactions when valid account number is provided then return OK")
    void testGetAllTransactionsWhenValidAccountNumberThenReturnOk() throws Exception {
        BigInteger accountNumber = new BigInteger("123456789");
        when(transactionService.getAllTransactions(accountNumber)).thenReturn(Collections.emptyList());
        when(transactionMapper.toListTransactionResponseDto(any(List.class))).thenReturn(Collections.singletonList(validTransactionResponseDto));

        mockMvc.perform(get("/api/v1/transactions")
                        .param("accountNumber", accountNumber.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].accountFrom").value(validTransactionResponseDto.getAccountFrom().toString()));
    }

    @Test
    @DisplayName("Test getAllTransactionsWithExceedLimit when valid account number is provided then return OK")
    void testGetAllTransactionsWithExceedLimitWhenValidAccountNumberThenReturnOk() throws Exception {
        BigInteger accountNumber = new BigInteger("123456789");
        when(transactionService.getAllTransactionsWithExceedLimit(accountNumber)).thenReturn(Collections.emptyList());
        when(transactionMapper.toListTransactionResponseDto(any(List.class))).thenReturn(Collections.singletonList(validTransactionResponseDto));

        mockMvc.perform(get("/api/v1/transactions/exceed")
                        .param("accountNumber", accountNumber.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].accountFrom").value(validTransactionResponseDto.getAccountFrom().toString()));
    }
}