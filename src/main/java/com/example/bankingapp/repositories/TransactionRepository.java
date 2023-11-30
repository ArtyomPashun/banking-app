package com.example.bankingapp.repositories;

import com.example.bankingapp.models.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByAccount_AccountNumberEquals(BigInteger accountNumber);
}
