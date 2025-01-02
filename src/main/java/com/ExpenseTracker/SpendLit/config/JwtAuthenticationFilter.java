package com.ExpenseTracker.SpendLit.config;

import com.ExpenseTracker.SpendLit.service.JWTService;
import com.ExpenseTracker.SpendLit.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Retrieve the Authorization header from the request
        final String authReader = request.getHeader("Authorization");
        final String jwt; // Variable to hold the JWT token
        final String userEmail; // Variable to hold the extracted user email

        // Check if the Authorization header is empty or does not start with "Bearer"
        if (StringUtils.isEmpty(authReader) || !StringUtils.startsWith(authReader, "Bearer")) {
            // If invalid, continue the filter chain without processing further
            filterChain.doFilter(request, response);
            return; // Exit to avoid further execution
        }

        // Extract the JWT by removing the "Bearer " prefix (first 7 characters)
        jwt = authReader.substring(7);

        // Use the JWTService to extract the username (email) from the token
        userEmail = jwtService.extractUserName(jwt);

        // If the userEmail is not empty and no authentication exists in the SecurityContext
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the UserService using the extracted userEmail
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

        }

    }
}
