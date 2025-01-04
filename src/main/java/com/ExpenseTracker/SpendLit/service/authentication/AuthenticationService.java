package com.ExpenseTracker.SpendLit.service.authentication;

import com.ExpenseTracker.SpendLit.dto.AuthDto.JwtAuthenticationResponse;
import com.ExpenseTracker.SpendLit.dto.AuthDto.RefreshTokenRequest;
import com.ExpenseTracker.SpendLit.dto.AuthDto.SignInRequest;
import com.ExpenseTracker.SpendLit.dto.AuthDto.SignUpRequest;
import com.ExpenseTracker.SpendLit.entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
