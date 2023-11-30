package com.example.bankingapp.services;

import com.example.bankingapp.models.entity.Account;
import com.example.bankingapp.models.entity.ExpenseCategory;
import com.example.bankingapp.models.entity.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface TransactionService {

    void createNewTransaction(Transaction transaction);

    List<Transaction> getAllTransactions(BigInteger accountNumber);

    List<Transaction> getAllTransactionsWithExceedLimit(BigInteger accountNumber);

    void saveAccountAndTransactionEntity(BigDecimal transactionSum, Account account, Transaction transaction, ExpenseCategory expenseCategory);
}
