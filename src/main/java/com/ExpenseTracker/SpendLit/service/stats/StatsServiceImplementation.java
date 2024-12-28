package com.ExpenseTracker.SpendLit.service.stats;

import com.ExpenseTracker.SpendLit.dto.GraphDto;
import com.ExpenseTracker.SpendLit.repository.ExpenseRepository;
import com.ExpenseTracker.SpendLit.repository.IncomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}
