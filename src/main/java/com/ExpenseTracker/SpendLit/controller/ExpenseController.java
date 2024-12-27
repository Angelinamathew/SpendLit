package com.ExpenseTracker.SpendLit.controller;

import com.ExpenseTracker.SpendLit.dto.ExpenseDto;
import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
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
    //To get all expenses
    @GetMapping("/all")
    public ResponseEntity<?> getAllExpense(){
        return ResponseEntity.ok(expenseService.getAllExpense());

    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        try {
            // Attempt to retrieve the expense by ID using the service layer.
            // Returns the found expense as the response body.
            return ResponseEntity.ok(expenseService.getExpenseById(id));
        } catch (EntityNotFoundException ex) {
            // Handle the case where the expense with the given ID is not found.
            // Return an HTTP 404 NOT FOUND response with the exception message as the response body.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            // Handle any unexpected exceptions that may occur during the operation.
            // Return an HTTP 500 INTERNAL SERVER ERROR response with a generic error message.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id, @RequestBody ExpenseDto dto) {
        try {
            // Attempt to update the expense by ID using the service layer.
            // Returns the updated expense as the response body.
            return ResponseEntity.ok(expenseService.updateExpense(id, dto));
        } catch (EntityNotFoundException ex) {
            // Handle the case where the expense with the given ID is not found.
            // Return an HTTP 404 NOT FOUND response with the exception message as the response body.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            // Handle any unexpected exceptions that may occur during the operation.
            // Return an HTTP 500 INTERNAL SERVER ERROR response with a generic error message.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        try {
            // Attempt to delete the expense by ID using the service layer.
            expenseService.deleteExpense(id);
            // Return an HTTP 200 OK response with no body if deletion is successful.
            return ResponseEntity.ok(null);
        } catch (EntityNotFoundException ex) {
            // Handle the case where the expense with the given ID is not found.
            // Return an HTTP 404 NOT FOUND response with the exception message as the response body.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            // Handle any unexpected exceptions that may occur during the operation.
            // Return an HTTP 500 INTERNAL SERVER ERROR response with a generic error message.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
        }
    }


}
