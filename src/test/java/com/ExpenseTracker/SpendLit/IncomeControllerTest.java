


package com.ExpenseTracker.SpendLit;

import com.ExpenseTracker.SpendLit.controller.IncomeController;
import com.ExpenseTracker.SpendLit.dto.IncomeDto;
import com.ExpenseTracker.SpendLit.entity.Income;
import com.ExpenseTracker.SpendLit.service.Income.IncomeService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IncomeControllerTest {

    @Mock
    private IncomeService incomeService;

    @InjectMocks
    private IncomeController incomeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostIncome_Success() {
        // Arrange
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setTitle("Salary");
        incomeDto.setAmount(5000);
        incomeDto.setDate(LocalDate.now());
        incomeDto.setCategory("Job");
        incomeDto.setDescription("Monthly salary");

        Income income = new Income();
        income.setId(1L);
        income.setTitle("Salary");
        income.setAmount(5000);
        income.setDate(LocalDate.now());
        income.setCategory("Job");
        income.setDescription("Monthly salary");

        when(incomeService.postIncome(incomeDto)).thenReturn(income);

        // Act
        ResponseEntity<?> response = incomeController.postIncome(incomeDto);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(income, response.getBody());
    }

    @Test
    void testPostIncome_Failure() {
        // Arrange
        IncomeDto incomeDto = new IncomeDto();
        when(incomeService.postIncome(incomeDto)).thenReturn(null);

        // Act
        ResponseEntity<?> response = incomeController.postIncome(incomeDto);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testGetAllIncome() {
        // Arrange
        Income income = new Income();
        income.setId(1L);
        income.setTitle("Salary");
        income.setAmount(5000);
        income.setDate(LocalDate.now());
        income.setCategory("Job");
        income.setDescription("Monthly salary");

        when(incomeService.getAllIncome()).thenReturn(List.of(income));

        // Act
        ResponseEntity<?> response = incomeController.getAllIncome();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(List.of(income), response.getBody());
    }

    @Test
    void testGetIncomeById_Success() {
        // Arrange
        Income income = new Income();
        income.setId(1L);
        income.setTitle("Salary");
        income.setAmount(5000);

        when(incomeService.getIncomeById(1L)).thenReturn(income);

        // Act
        ResponseEntity<?> response = incomeController.getIncomeById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(income, response.getBody());
    }

    @Test
    void testGetIncomeById_NotFound() {
        // Arrange
        when(incomeService.getIncomeById(1L)).thenThrow(new EntityNotFoundException("Income not found"));

        // Act
        ResponseEntity<?> response = incomeController.getIncomeById(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Income not found", response.getBody());
    }

    @Test
    void testDeleteIncome_Success() {
        // Act
        ResponseEntity<?> response = incomeController.deleteIncome(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(incomeService, times(1)).deleteIncome(1L);
    }

    @Test
    void testDeleteIncome_NotFound() {
        // Arrange
        doThrow(new EntityNotFoundException("Income not found")).when(incomeService).deleteIncome(1L);

        // Act
        ResponseEntity<?> response = incomeController.deleteIncome(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Income not found", response.getBody());
    }
}

