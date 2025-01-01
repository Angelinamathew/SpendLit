package com.ExpenseTracker.SpendLit.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTServiceImplementation {

    //jwt.secret is injected from application.properties
    @Value("${jwt.secret}")
    private String base64Secret;

    // Method to generate JWT token for a given UserDetails
    private String generateToken(UserDetails userDetails) {
        return Jwts.builder()  // Start building the JWT
                .setSubject(userDetails.getUsername())  // Set the subject (username) of the token
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set the issuance date (current time)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))  // Set expiration time (24 hours)
                .signWith(getSignKey(), SignatureAlgorithm.ES256)  // Sign the token using ES256 algorithm
                .compact();  // Compact the JWT into a string format
    }



    // Method to generate a signing key from a base64-encoded secret
    private Key getSignKey() {
        // Decode the base64-encoded secret
        byte[] key = Base64.getDecoder().decode(base64Secret);
        return Keys.hmacShaKeyFor(key);  // Generate HMAC key for signing
    }
}