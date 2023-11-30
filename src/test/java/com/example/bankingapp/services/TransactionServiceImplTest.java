package com.example.bankingapp.services;

import com.example.bankingapp.models.entity.Account;
import com.example.bankingapp.models.entity.ExpenseCategory;
import com.example.bankingapp.models.entity.Transaction;
import com.example.bankingapp.repositories.AccountRepository;
import com.example.bankingapp.repositories.ExpenseCategoryRepository;
import com.example.bankingapp.repositories.TransactionRepository;
import com.example.bankingapp.services.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ExpenseCategoryRepository expenseCategoryRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction validTransaction;
    private Account validAccount;
    private ExpenseCategory validExpenseCategory;

    @BeforeEach
    void setUp() {
        validAccount = new Account(1, new BigInteger("123456789"), BigDecimal.valueOf(1000), BigDecimal.valueOf(5000), LocalDateTime.now(), "USD");
        validExpenseCategory = new ExpenseCategory(1, "services");
        validTransaction = new Transaction(1, validAccount, new BigInteger("987654321"), "USD", BigDecimal.valueOf(100), validExpenseCategory, LocalDateTime.now(), false);
    }

    @Test
    void testCreateNewTransactionWhenValidTransactionThenNoException() {
        when(accountService.isAccountPresent(validTransaction.getAccount().getAccountNumber())).thenReturn(true);
        when(accountService.isLimitValid(validTransaction.getSum())).thenReturn(true);
        when(accountRepository.findByAccountNumber(validTransaction.getAccount().getAccountNumber())).thenReturn(validAccount);
        when(expenseCategoryRepository.findByName(validTransaction.getExpenseCategory().getName())).thenReturn(validExpenseCategory);

        transactionService.createNewTransaction(validTransaction);

        verify(accountRepository).findByAccountNumber(validTransaction.getAccount().getAccountNumber());
        verify(expenseCategoryRepository).findByName(validTransaction.getExpenseCategory().getName());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void testGetAllTransactionsWhenValidAccountNumberThenReturnTransactions() {
        BigInteger accountNumber = validAccount.getAccountNumber();
        List<Transaction> expectedTransactions = Collections.singletonList(validTransaction);
        when(transactionRepository.findAllByAccount_AccountNumberEquals(accountNumber)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionService.getAllTransactions(accountNumber);

        assertThat(actualTransactions).isEqualTo(expectedTransactions);
    }

    @Test
    void testGetAllTransactionsWithExceedLimitWhenValidAccountNumberThenReturnTransactions() {
        BigInteger accountNumber = validAccount.getAccountNumber();
        List<Transaction> allTransactions = List.of(
                validTransaction,
                new Transaction(2, validAccount, new BigInteger("987654321"), "USD", BigDecimal.valueOf(200), validExpenseCategory, LocalDateTime.now(), true)
        );
        List<Transaction> expectedTransactions = List.of(allTransactions.get(1));
        when(transactionRepository.findAllByAccount_AccountNumberEquals(accountNumber)).thenReturn(allTransactions);

        List<Transaction> actualTransactions = transactionService.getAllTransactionsWithExceedLimit(accountNumber);

        assertThat(actualTransactions).isEqualTo(expectedTransactions);
    }

    @Test
    void testSaveAccountAndTransactionEntityWhenValidParametersThenSaveEntities() {
        BigDecimal transactionSum = BigDecimal.valueOf(100);
        Account account = validAccount;
        Transaction transaction = validTransaction;
        ExpenseCategory expenseCategory = validExpenseCategory;

        transactionService.saveAccountAndTransactionEntity(transactionSum, account, transaction, expenseCategory);

        verify(accountRepository).save(account);
        verify(transactionRepository).save(transaction);
    }
}