package com.ExpenseTracker.SpendLit.repository;

import com.ExpenseTracker.SpendLit.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    //Retrieves a list of Income entities whose incomeDate falls between the specified start and end dates.
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
