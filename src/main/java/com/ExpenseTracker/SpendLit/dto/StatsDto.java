package com.ExpenseTracker.SpendLit.dto;

import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.entity.Income;
import lombok.Data;

@Data
public class StatsDto {

    private double income;

    private double expense;

    private Income lastestIncome;

    private Expense lastestExpense;

    private Double balance;

    private Double minIncome;

    private Double maxIncome;

    private Double minExpense;

    private Double maxExpense;
}
