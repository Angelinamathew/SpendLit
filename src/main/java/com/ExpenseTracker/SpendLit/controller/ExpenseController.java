package com.ExpenseTracker.SpendLit.controller;

import com.ExpenseTracker.SpendLit.dto.ExpenseDto;
import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ExpenseController {

    private final ExpenseService expenseService;
    /**
     * Endpoint to handle POST requests for creating a new expense.
     * Accepts a JSON representation of ExpenseDto, converts it to an Expense, and saves it.
     *
     * @param expenseDto The request body containing the expense data
     * @return ResponseEntity with the created Expense object and appropriate HTTP status
     */
    @PostMapping
    public ResponseEntity<?> postExpense(@RequestBody ExpenseDto expenseDto) {
        // Call the service layer to save the expense
        Expense createdExpense = expenseService.postExpense(expenseDto);

        // Return HTTP 201 Created status if successful, otherwise 400 Bad Request
        if (createdExpense != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
