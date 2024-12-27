package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.ExpenseDto;
import com.ExpenseTracker.SpendLit.dto.IncomeDto;
import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.entity.Income;
import com.ExpenseTracker.SpendLit.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    // created post income API call
    public Income postIncome(IncomeDto incomeDto){
        return saveOrUpdateIncome(new Income(), incomeDto);
    }
    // created get all income API call with sorting
    public List<Income> getAllIncome() {

        return incomeRepository.findAll()
                // Convert the list of expenses into a stream for functional operations
                .stream()
                // Sort the stream of expenses in descending order by the date field
                .sorted(Comparator.comparing(Income::getDate).reversed())
                // Collect the sorted stream back into a list and return it
                .collect(Collectors.toList());
    }

    //created Update Income API call
    public Income updateIncome(Long id, IncomeDto dto){
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if(optionalIncome.isPresent()){
            return saveOrUpdateIncome(optionalIncome.get(), dto);
        }else{
            throw new EntityNotFoundException("Income is not found with id "+ id);
        }
    }

    // created get Income by ID
    public Income getIncomeById(Long id){
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if(optionalIncome.isPresent()){
            return optionalIncome.get();
        }else{
            throw new EntityNotFoundException("Income is not found with id "+id);
        }
    }
    // created an API call to delete an income using id
    public void deleteIncome(Long id){
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if(optionalIncome.isPresent()){
            incomeRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Expense is not found with id "+ id);
        }
    }

}
