package com.ExpenseTracker.SpendLit.repository;

import com.ExpenseTracker.SpendLit.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long > {

   // Retrieves a list of Income entities whose incomeDate falls between the specified start and end dates.

    List<Income> findByDateBetween(LocalDate startDate, LocalDate endDate);

     //Custom query to calculate the total sum of the amount field across all Income records.
     //return the total sum of all incomes
    @Query ("SELECT SUM(i.amount) FROM Income i")
    Double sumAllAmounts();

    //Retrieves the most recent Expense entity based on the date field in descending order.
    Optional<Income> findFirstByOrderByDateDesc();
}
