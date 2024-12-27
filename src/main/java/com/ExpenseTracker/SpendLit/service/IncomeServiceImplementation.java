package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.ExpenseDto;
import com.ExpenseTracker.SpendLit.dto.IncomeDto;
import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.entity.Income;
import com.ExpenseTracker.SpendLit.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeServiceImplementation implements IncomeService{

    private final IncomeRepository incomeRepository;

   // Updates or saves an Expense entity using the data from an ExpenseDto.
    private Income saveOrUpdateIncome(Income income, IncomeDto incomeDto){
        income.setTitle(incomeDto.getTitle());
        income.setAmount(incomeDto.getAmount());
        income.setDate(incomeDto.getDate());
        income.setCategory(incomeDto.getCategory());
        income.setDescription(incomeDto.getDescription());

        return incomeRepository.save(income);

    }
    public Income postIncome(IncomeDto incomeDto){
        return saveOrUpdateIncome(new Income(), incomeDto);
    }

}
