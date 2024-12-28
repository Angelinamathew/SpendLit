package com.ExpenseTracker.SpendLit.service.expense;

import com.ExpenseTracker.SpendLit.dto.ExpenseDto;
import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.repository.ExpenseRepository;
import com.ExpenseTracker.SpendLit.service.expense.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImplementation implements ExpenseService {

    private final ExpenseRepository expenseRepository;


    /**
     * Updates or saves an Expense entity using the data from an ExpenseDto.
     * If the provided Expense is new, this method acts as a creation process.
     * Otherwise, it updates the existing Expense with new data.
     *
     * @param expense The existing or new Expense entity
     * @param expenseDto The DTO containing updated data
     * @return The saved Expense entity
     */

    public Expense updateOrSaveExpense(Expense expense, ExpenseDto expenseDto){
        // Map fields from ExpenseDto to Expense entity
        expense.setTitle(expenseDto.getTitle());
        expense.setDate(expenseDto.getDate());
        expense.setAmount(expenseDto.getAmount());
        expense.setCategory(expenseDto.getCategory());
        expense.setDescription(expenseDto.getDescription());

        // Save the Expense entity to the database and return the saved entity
        return expenseRepository.save(expense);
    }
    /**
     * Creates a new Expense entity and persists it to the database.
     *
     * @param expenseDto The DTO containing the data for the new Expense
     * @return The saved Expense entity
     */
    public Expense postExpense(ExpenseDto expenseDto){
        return updateOrSaveExpense(new Expense(), expenseDto);
    }
    //get all the expenses
    public List<Expense> getAllExpense() {
        // Fetch all expenses from the database using the repository
        return expenseRepository.findAll()
                // Convert the list of expenses into a stream for functional operations
                .stream()
                // Sort the stream of expenses in descending order by the date field
                .sorted(Comparator.comparing(Expense::getDate).reversed())
                // Collect the sorted stream back into a list and return it
                .collect(Collectors.toList());
    }
    // To get expense by ID
    public Expense getExpenseById(Long id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            return optionalExpense.get();
        }else{
            throw new EntityNotFoundException("Expense is not found with id "+id);
        }
    }
    //To update the expense
    public Expense updateExpense(Long id, ExpenseDto dto){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            return updateOrSaveExpense(optionalExpense.get(), dto);
        }else{
            throw new EntityNotFoundException("Expense is not found with id "+ id);
        }
    }
    // to delete the expense
    public void deleteExpense(Long id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if(optionalExpense.isPresent()){
            expenseRepository.deleteById(id);
        }else{
            throw new EntityNotFoundException("Expense is not found with id "+ id);
        }
    }


}
