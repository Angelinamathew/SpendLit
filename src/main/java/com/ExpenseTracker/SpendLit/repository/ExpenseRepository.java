package com.ExpenseTracker.SpendLit.repository;

import com.ExpenseTracker.SpendLit.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
