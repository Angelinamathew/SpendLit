package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.JwtAuthenticationResponse;
import com.ExpenseTracker.SpendLit.dto.RefreshTokenRequest;
import com.ExpenseTracker.SpendLit.dto.SignInRequest;
import com.ExpenseTracker.SpendLit.dto.SignUpRequest;
import com.ExpenseTracker.SpendLit.entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
