package com.ExpenseTracker.SpendLit.repository;

import com.ExpenseTracker.SpendLit.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    //Retrieves a list of Income entities whose incomeDate falls between the specified start and end dates.
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    /**
     * Custom query to calculate the total sum of the amount field across all Expense records.
     * @return the total sum of all expenses
     */
    @Query("SELECT SUM(e.amount) FROM Expense e")
    Double sumAllAmounts();

    Optional<Expense> findFirstByOrderByDateDesc();
}
