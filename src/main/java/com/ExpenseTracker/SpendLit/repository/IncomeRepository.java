package com.ExpenseTracker.SpendLit.repository;

import com.ExpenseTracker.SpendLit.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long > {
}
