package com.example.bankingapp.repositories;

import com.example.bankingapp.models.entity.ExchangeCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeCurrencyRepository extends JpaRepository<ExchangeCurrency, Integer> {

    List<ExchangeCurrency> findAllByCurrencyCodeEquals(String currencyCode);
}
