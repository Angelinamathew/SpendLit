package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.SignUpRequest;
import com.ExpenseTracker.SpendLit.entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);
}
