package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.IncomeDto;
import com.ExpenseTracker.SpendLit.entity.Income;

import java.util.List;

public interface IncomeService {
    Income postIncome(IncomeDto incomeDto);
    List<Income> getAllIncome();
    Income updateIncome(Long id, IncomeDto dto);
    Income getIncomeById(Long id);
    void deleteIncome(Long id);
}
