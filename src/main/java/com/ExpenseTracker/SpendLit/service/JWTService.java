package com.ExpenseTracker.SpendLit.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String generateToken(UserDetails userDetails);

    String extractUserName(String token);
}
