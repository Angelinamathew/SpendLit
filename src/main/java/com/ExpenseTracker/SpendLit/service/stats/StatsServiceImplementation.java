package com.ExpenseTracker.SpendLit.service.stats;

import com.ExpenseTracker.SpendLit.repository.ExpenseRepository;
import com.ExpenseTracker.SpendLit.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsServiceImplementation {

    private final IncomeRepository incomeRepository;
    private final ExpenseRepository expenseRepository;
}
