package com.example.bankingapp.services.impl;

import com.example.bankingapp.exceptions.EntityNotFoundException;
import com.example.bankingapp.models.entity.Account;
import com.example.bankingapp.repositories.AccountRepository;
import com.example.bankingapp.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void updateAccountLimit(BigInteger accountNumber, BigDecimal limit) {
        if (!isAccountPresent(accountNumber)) {
            throw new EntityNotFoundException("There is no account with such account number.");
        } else if (!isLimitValid(limit)) {
            throw new IllegalArgumentException("The limit should be positive.");
        } else {
            Account currentAccount = accountRepository.findByAccountNumber(accountNumber);
            BigDecimal temp = limit.subtract(currentAccount.getCurrentLimit());
            currentAccount.setCurrentLimit(limit);
            currentAccount.setMonthlyLimit(currentAccount.getMonthlyLimit().add(temp));
            currentAccount.setLimitSettingDate(LocalDateTime.now());
            accountRepository.save(currentAccount);
        }
    }

    @Override
    public boolean isAccountPresent(BigInteger accountNumber) {
        return accountRepository.existsAccountByAccountNumber(accountNumber);
    }

    @Override
    public boolean isLimitValid(BigDecimal limit) {
        return limit.compareTo(BigDecimal.ZERO) > 0;
    }
}
