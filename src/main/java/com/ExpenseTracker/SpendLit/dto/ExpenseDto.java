package com.ExpenseTracker.SpendLit.dto;

import lombok.Data;

import java.time.LocalDate;
// DTO (Data Transfer Object) class for transferring expense data between layers
// The @Data annotation from Lombok automatically generates getters, setters, and other utility methods
@Data
public class ExpenseDto {

    private Long id;

    private String title;

    private String description;

    private String category;

    private LocalDate date;

    private Integer amount;
}
