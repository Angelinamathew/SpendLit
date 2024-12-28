package com.ExpenseTracker.SpendLit.service.stats;

import com.ExpenseTracker.SpendLit.dto.GraphDto;
import com.ExpenseTracker.SpendLit.dto.StatsDto;
import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.entity.Income;
import com.ExpenseTracker.SpendLit.repository.ExpenseRepository;
import com.ExpenseTracker.SpendLit.repository.IncomeRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsServiceImplementation implements StatsService{
    // Repositories for accessing income and expense data from the database
    private final IncomeRepository incomeRepository;

    private final ExpenseRepository expenseRepository;

    /**
     * This method fetches expense and income data for the last 28 days
     * and returns it encapsulated within a GraphDto object.
     *
     * @return GraphDto containing lists of expenses and incomes
     */
    //created API to get income and expense data for graph
    public GraphDto getGraphCharts() {
        // Get the current date as the end date
        LocalDate endDate = LocalDate.now();

        // Calculate the start date, 27 days before the end date (total of 28 days)
        LocalDate startDate = endDate.minusDays(27);

        // Create a new GraphDto object to store the data
        GraphDto graphDto = new GraphDto();

        // Fetch expenses between the start and end dates from the repository
        graphDto.setExpenseList(expenseRepository.findByDateBetween(startDate, endDate));

        // Fetch incomes between the start and end dates from the repository
        graphDto.setIncomeList(incomeRepository.findByDateBetween(startDate, endDate));

        // Return the populated GraphDto object
        return graphDto;
    }

    //Created Stats API for total & latest Income and Expense
    public StatsDto getStats(){
        // Retrieve the total sum of all incomes and expenses
        Double totalIncome = incomeRepository.sumAllAmounts();
        Double totalExpense = expenseRepository.sumAllAmounts();

        // Get the most recent income and expense records
        Optional<Income> optionalIncome = incomeRepository.findFirstByOrderByDateDesc();
        Optional<Expense> optionalExpense = expenseRepository.findFirstByOrderByDateDesc();

        // Create and populate a StatsDto object
        StatsDto statsDto = new StatsDto();
        statsDto.setExpense(totalExpense);
        statsDto.setIncome(totalIncome);

        // Set the most recent income and expense records if present
        optionalIncome.ifPresent(statsDto::setLastestIncome);
        optionalExpense.ifPresent(statsDto::setLastestExpense);

        return statsDto;
    }
}
