package com.ExpenseTracker.SpendLit;

import com.ExpenseTracker.SpendLit.controller.ExpenseController;
import com.ExpenseTracker.SpendLit.dto.ExpenseDto;
import com.ExpenseTracker.SpendLit.entity.Expense;
import com.ExpenseTracker.SpendLit.service.expense.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @InjectMocks
    private ExpenseController expenseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void postExpense_Success() {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setTitle("Groceries");
        expenseDto.setDate(LocalDate.now());
        expenseDto.setAmount(100);
        expenseDto.setCategory("Food");
        expenseDto.setDescription("Weekly groceries");

        Expense expense = new Expense();
        expense.setId(1L);
        expense.setTitle("Groceries");
        expense.setDate(LocalDate.now());
        expense.setAmount(100);
        expense.setCategory("Food");
        expense.setDescription("Weekly groceries");

        when(expenseService.postExpense(expenseDto)).thenReturn(expense);

        ResponseEntity<?> response = expenseController.postExpense(expenseDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expense, response.getBody());
    }

    @Test
    void postExpense_Failure() {
        ExpenseDto expenseDto = new ExpenseDto();

        when(expenseService.postExpense(expenseDto)).thenReturn(null);

        ResponseEntity<?> response = expenseController.postExpense(expenseDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllExpense_Success() {
        Expense expense1 = new Expense();
        expense1.setId(1L);
        expense1.setTitle("Groceries");

        Expense expense2 = new Expense();
        expense2.setId(2L);
        expense2.setTitle("Utilities");

        List<Expense> expenses = Arrays.asList(expense1, expense2);

        when(expenseService.getAllExpense()).thenReturn(expenses);

        ResponseEntity<?> response = expenseController.getAllExpense();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expenses, response.getBody());
    }

    @Test
    void getExpenseById_Success() {
        Long id = 1L;
        Expense expense = new Expense();
        expense.setId(id);
        expense.setTitle("Groceries");

        when(expenseService.getExpenseById(id)).thenReturn(expense);

        ResponseEntity<?> response = expenseController.getExpenseById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expense, response.getBody());
    }

    @Test
    void getExpenseById_NotFound() {
        Long id = 1L;

        when(expenseService.getExpenseById(id)).thenThrow(new EntityNotFoundException("Expense not found"));

        ResponseEntity<?> response = expenseController.getExpenseById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Expense not found", response.getBody());
    }

    @Test
    void updateExpense_Success() {
        Long id = 1L;
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setTitle("Updated Title");

        Expense updatedExpense = new Expense();
        updatedExpense.setId(id);
        updatedExpense.setTitle("Updated Title");

        when(expenseService.updateExpense(id, expenseDto)).thenReturn(updatedExpense);

        ResponseEntity<?> response = expenseController.updateExpense(id, expenseDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedExpense, response.getBody());
    }

    @Test
    void updateExpense_NotFound() {
        Long id = 1L;
        ExpenseDto expenseDto = new ExpenseDto();

        when(expenseService.updateExpense(id, expenseDto)).thenThrow(new EntityNotFoundException("Expense not found"));

        ResponseEntity<?> response = expenseController.updateExpense(id, expenseDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Expense not found", response.getBody());
    }

    @Test
    void deleteExpense_Success() {
        Long id = 1L;

        doNothing().when(expenseService).deleteExpense(id);

        ResponseEntity<?> response = expenseController.deleteExpense(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteExpense_NotFound() {
        Long id = 1L;

        doThrow(new EntityNotFoundException("Expense not found")).when(expenseService).deleteExpense(id);

        ResponseEntity<?> response = expenseController.deleteExpense(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Expense not found", response.getBody());
    }
}
