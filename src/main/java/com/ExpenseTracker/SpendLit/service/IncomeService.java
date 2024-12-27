package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.IncomeDto;
import com.ExpenseTracker.SpendLit.entity.Income;

    public interface IncomeService {
    Income postIncome(IncomeDto incomeDto);
}
