package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.ExpenseDto;
import com.ExpenseTracker.SpendLit.entity.Expense;

import java.util.List;

public interface ExpenseService {
    Expense postExpense(ExpenseDto expenseDto);
    List<Expense> getAllExpense();
    Expense getExpenseById(Long id);
    Expense updateExpense(Long id, ExpenseDto dto);
    void deleteExpense(Long id);
}
