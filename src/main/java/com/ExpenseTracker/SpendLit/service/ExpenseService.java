package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.ExpenseDto;
import com.ExpenseTracker.SpendLit.entity.Expense;

public interface ExpenseService {
    Expense postExpense(ExpenseDto expenseDto);
}
