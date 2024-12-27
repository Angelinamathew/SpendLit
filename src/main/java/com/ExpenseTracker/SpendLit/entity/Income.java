package com.ExpenseTracker.SpendLit.entity;

import com.ExpenseTracker.SpendLit.dto.IncomeDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String title;

    private Integer amount;

    private LocalDate date;

    private String category;

    private String description;

    /**
     * Converts the Income entity to an IncomeDto object.
     * This is useful for transferring data in a structured format without exposing entity details.
     *
     * @return an IncomeDto object populated with data from this Income entity
     */
//    public IncomeDto getIncomeDto() {
//        // Create a new IncomeDto instance
//        IncomeDto incomeDto = new IncomeDto();
//
//        // Map fields from the entity to the DTO
//        incomeDto.setId(id);
//        incomeDto.setTitle(title);
//        incomeDto.setAmount(amount);
//        incomeDto.setDate(date);
//        incomeDto.setCategory(category);
//        incomeDto.setDescription(description);
//
//        return incomeDto; // Return the populated DTO object
//    }

}
