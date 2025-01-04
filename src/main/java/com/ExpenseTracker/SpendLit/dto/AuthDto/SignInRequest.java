package com.ExpenseTracker.SpendLit.dto.AuthDto;

import lombok.Data;

@Data
public class SignInRequest {

    private String email;

    private String password;
}
