package com.example.bankingapp.repositories;

import com.example.bankingapp.models.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByAccountNumber(BigInteger accountNumber);

    boolean existsAccountByAccountNumber(BigInteger accountNumber);
}

