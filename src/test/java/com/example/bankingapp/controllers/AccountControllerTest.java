package com.example.bankingapp.controllers;

import com.example.bankingapp.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testUpdateAccountLimitWhenAccountNumberAndLimitAreValidThenReturnOk() throws Exception {
        BigInteger validAccountNumber = new BigInteger("123456789");
        BigDecimal validLimit = new BigDecimal("1000.00");

        doNothing().when(accountService).updateAccountLimit(validAccountNumber, validLimit);

        mockMvc.perform(patch("/api/v1/accounts/update")
                        .param("accountNumber", validAccountNumber.toString())
                        .param("limit", validLimit.toString()))
                .andExpect(status().isOk());

        verify(accountService).updateAccountLimit(validAccountNumber, validLimit);
    }
}