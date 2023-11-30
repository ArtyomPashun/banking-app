package com.example.bankingapp.services;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface AccountService {

    void updateAccountLimit(BigInteger accountNumber, BigDecimal limit);

    public boolean isAccountPresent(BigInteger accountNumber);

    public boolean isLimitValid(BigDecimal limit);
}
