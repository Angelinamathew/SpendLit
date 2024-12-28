package com.ExpenseTracker.SpendLit.repository;

import com.ExpenseTracker.SpendLit.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long > {

   // Retrieves a list of Income entities whose incomeDate falls between the specified start and end dates.

    List<Income> findByBetweenDate(LocalDate startDate, LocalDate endDate);
}
