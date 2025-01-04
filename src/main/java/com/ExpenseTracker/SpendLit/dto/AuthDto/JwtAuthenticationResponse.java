package com.ExpenseTracker.SpendLit.dto.AuthDto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String token;

    private String refreshToken;

}
