package com.example.bankingapp.services.impl;

import com.example.bankingapp.exceptions.IncorrectCurrencyException;
import com.example.bankingapp.exceptions.IncorrectLimitException;
import com.example.bankingapp.models.entity.Account;
import com.example.bankingapp.models.entity.ExpenseCategory;
import com.example.bankingapp.models.entity.Transaction;
import com.example.bankingapp.repositories.AccountRepository;
import com.example.bankingapp.repositories.ExpenseCategoryRepository;
import com.example.bankingapp.repositories.TransactionRepository;
import com.example.bankingapp.services.AccountService;
import com.example.bankingapp.services.ExchangeCurrencyService;
import com.example.bankingapp.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final ExpenseCategoryRepository expenseCategoryRepository;

    private final AccountRepository accountRepository;

    private final AccountService accountService;

    private final ExchangeCurrencyService exchangeCurrencyService;

    @Override
    @Transactional
    public void createNewTransaction(Transaction transaction) {
        String currency = transaction.getCurrency();
        if (accountService.isAccountPresent(transaction.getAccount().getAccountNumber()) && accountService.isLimitValid(transaction.getSum())) {
            Account account = accountRepository.findByAccountNumber(transaction.getAccount().getAccountNumber());
            ExpenseCategory expenseCategory = expenseCategoryRepository.findByName(transaction.getExpenseCategory().getName());
            if (currency.equalsIgnoreCase("USD")) {
                saveAccountAndTransactionEntity(transaction.getSum(), account, transaction, expenseCategory);
            } else if (currency.equalsIgnoreCase("KZT") || currency.equalsIgnoreCase("RUB")) {
                BigDecimal currencyRate = exchangeCurrencyService.getExchangeRate("USD/" + currency).getRate();
                BigDecimal sumInUSD = transaction.getSum().divide(currencyRate, 2, RoundingMode.HALF_UP);
                saveAccountAndTransactionEntity(sumInUSD, account, transaction, expenseCategory);
            } else {
                throw new IncorrectCurrencyException("There is no opportunity to make transaction in such currency.");
            }
        } else {
            throw new IncorrectLimitException("There is no such account or transaction limit less 0.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions(BigInteger accountNumber) {
        return transactionRepository.findAllByAccount_AccountNumberEquals(accountNumber);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactionsWithExceedLimit(BigInteger accountNumber) {
        return transactionRepository.findAllByAccount_AccountNumberEquals(accountNumber)
                .stream()
                .filter(Transaction::getIsLimitExceed)
                .toList();
    }

    @Override
    @Transactional
    public void saveAccountAndTransactionEntity(BigDecimal transactionSum, Account account, Transaction transaction, ExpenseCategory expenseCategory) {
        BigDecimal currentAccountLimit = account.getMonthlyLimit();
        boolean flag = currentAccountLimit.compareTo(transactionSum) >= 0;
        account.setMonthlyLimit(currentAccountLimit.subtract(transactionSum));
        transaction.setIsLimitExceed(!flag);
        transaction.setDate(LocalDateTime.now());
        transaction.setAccount(account);
        transaction.setExpenseCategory(expenseCategory);
        accountRepository.save(account);
        transactionRepository.save(transaction);
    }
}
