package com.ExpenseTracker.SpendLit.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTServiceImplementation implements JWTService {

    //jwt.secret is injected from application.properties
    @Value("${jwt.secret}")
    private String base64Secret;

    // Method to generate JWT token for a given UserDetails
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()  // Start building the JWT
                .setSubject(userDetails.getUsername())  // Set the subject (username) of the token
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set the issuance date (current time)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))  // Set expiration time (24 hours)
                .signWith(getSignKey(), SignatureAlgorithm.ES256)  // Sign the token using ES256 algorithm
                .compact();  // Compact the JWT into a string format
    }
    //// Method to extract claims from a JWT token
    private <T> T extractClaims(String token, Function<Claims, T> claimResolver){
        // Extract all claims from the token
        final Claims claims = extractAllClaims(token);
        // Apply the claimResolver function to get the desired claim
        return claimResolver.apply(claims);
    }
    /*
     Method to extract claims from a JWT token
     - Create a builder for JWT parsing.
     - Set the signing key used for verifying the JWT.
     - Build the JWT parser with the specified signing key
     - Parse the JWT token, extracting the body (claims) and validating the signature.
     - Retrieve the claims from the parsed JWT Security Object (JWS).
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();

    }
    // This will return the email for the particular token
    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);

    }

    // Method to generate a signing key from a base64-encoded secret
    private Key getSignKey() {
        // Decode the base64-encoded secret
        byte[] key = Base64.getDecoder().decode(base64Secret);
        return Keys.hmacShaKeyFor(key);  // Generate HMAC key for signing
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        // Extract the username from the token
        final String userName = extractUserName(token);
        // Check if the username in the token matches the username from userDetails
        // and if the token is not expired
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        // Extract the expiration date from the token and check if it is before the current date
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}