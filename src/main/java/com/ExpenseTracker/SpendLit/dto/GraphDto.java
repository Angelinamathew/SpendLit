package com.ExpenseTracker.SpendLit.dto;

import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.entity.Income;
import lombok.Data;

import java.util.List;
@Data
public class GraphDto {

    private List<Expense> expenseList;
    private List<Income> incomeList;


}
