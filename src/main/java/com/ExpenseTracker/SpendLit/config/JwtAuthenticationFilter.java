package com.ExpenseTracker.SpendLit.config;

import com.ExpenseTracker.SpendLit.service.jwt.JWTService;
import com.ExpenseTracker.SpendLit.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
        final String authHeader = request.getHeader("Authorization");
        final String jwt; // Variable to store the extracted JWT token
        final String userEmail; // Variable to store the extracted user email

        // Check if the Authorization header is missing or does not start with "Bearer"
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer")) {
            // If invalid, continue the filter chain without processing further
            filterChain.doFilter(request, response);
            return; // Exit to avoid further execution
        }

        // Extract the JWT token by removing the "Bearer " prefix (first 7 characters)
        jwt = authHeader.substring(7);

        // Use the JWTService to extract the username (email) from the JWT token
        userEmail = jwtService.extractUserName(jwt);

        // Proceed if the userEmail is not empty and no authentication exists in the current SecurityContext
        if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the UserService using the extracted userEmail
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            // Validate the token using JWTService
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Create a new SecurityContext and set the authentication details
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                // Create an authentication token with the user's details and authorities
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // Set additional details for the token from the request
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the SecurityContext
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            }
        }

        // Continue with the next filter in the chain
        filterChain.doFilter(request, response);

    }
}
