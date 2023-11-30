package com.example.bankingapp.repositories;

import com.example.bankingapp.models.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Integer> {

    ExpenseCategory findByName (String Name);
}
