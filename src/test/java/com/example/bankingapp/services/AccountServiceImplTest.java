package com.example.bankingapp.services;

import com.example.bankingapp.exceptions.EntityNotFoundException;
import com.example.bankingapp.exceptions.IncorrectLimitException;
import com.example.bankingapp.models.entity.Account;
import com.example.bankingapp.repositories.AccountRepository;
import com.example.bankingapp.services.impl.AccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("test updateAccountLimit when account is present and limit is valid")
    void testUpdateAccountLimitWhenAccountIsPresentAndLimitIsValidThenLimitIsUpdated() {
        BigInteger accountNumber = new BigInteger("123456789");
        BigDecimal newLimit = new BigDecimal("5000");
        Account account = new Account(1, accountNumber, new BigDecimal("3000"), new BigDecimal("10000"), LocalDateTime.now(), "USD");

        when(accountRepository.existsAccountByAccountNumber(accountNumber)).thenReturn(true);
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(account);

        accountService.updateAccountLimit(accountNumber, newLimit);

        assertEquals(newLimit, account.getCurrentLimit());
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("test updateAccountLimit when account is not present")
    void testUpdateAccountLimitWhenAccountIsNotPresentThenEntityNotFoundExceptionIsThrown() {
        BigInteger accountNumber = new BigInteger("123456789");
        BigDecimal newLimit = new BigDecimal("5000");

        when(accountRepository.existsAccountByAccountNumber(accountNumber)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> accountService.updateAccountLimit(accountNumber, newLimit));
    }

    @Test
    @DisplayName("test updateAccountLimit when limit is not valid")
    void testUpdateAccountLimitWhenLimitIsNotValidThenIncorrectLimitExceptionIsThrown() {
        BigInteger accountNumber = new BigInteger("123456789");
        BigDecimal invalidLimit = new BigDecimal("-100");

        when(accountRepository.existsAccountByAccountNumber(accountNumber)).thenReturn(true);

        assertThrows(IncorrectLimitException.class, () -> accountService.updateAccountLimit(accountNumber, invalidLimit));
    }

    @Test
    @DisplayName("test isAccountPresent when account is present")
    void testIsAccountPresentWhenAccountIsPresentThenTrueIsReturned() {
        BigInteger accountNumber = new BigInteger("123456789");

        when(accountRepository.existsAccountByAccountNumber(accountNumber)).thenReturn(true);

        assertTrue(accountService.isAccountPresent(accountNumber));
    }

    @Test
    @DisplayName("test isAccountPresent when account is not present")
    void testIsAccountPresentWhenAccountIsNotPresentThenFalseIsReturned() {
        BigInteger accountNumber = new BigInteger("123456789");

        when(accountRepository.existsAccountByAccountNumber(accountNumber)).thenReturn(false);

        assertFalse(accountService.isAccountPresent(accountNumber));
    }

    @Test
    @DisplayName("test isLimitValid when limit is positive")
    void testIsLimitValidWhenLimitIsPositiveThenTrueIsReturned() {
        BigDecimal positiveLimit = new BigDecimal("100");

        assertTrue(accountService.isLimitValid(positiveLimit));
    }

    @Test
    @DisplayName("test isLimitValid when limit is zero")
    void testIsLimitValidWhenLimitIsZeroThenFalseIsReturned() {
        BigDecimal zeroLimit = BigDecimal.ZERO;

        assertFalse(accountService.isLimitValid(zeroLimit));
    }

    @Test
    @DisplayName("test isLimitValid when limit is negative")
    void testIsLimitValidWhenLimitIsNegativeThenFalseIsReturned() {
        BigDecimal negativeLimit = new BigDecimal("-100");

        assertFalse(accountService.isLimitValid(negativeLimit));
    }
}