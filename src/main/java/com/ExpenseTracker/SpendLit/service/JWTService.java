package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JWTService {
    String generateToken(UserDetails userDetails);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);


}
