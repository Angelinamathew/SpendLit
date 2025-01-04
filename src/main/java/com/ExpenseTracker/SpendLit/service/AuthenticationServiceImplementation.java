package com.ExpenseTracker.SpendLit.service;

import com.ExpenseTracker.SpendLit.dto.JwtAuthenticationResponse;
import com.ExpenseTracker.SpendLit.dto.RefreshTokenRequest;
import com.ExpenseTracker.SpendLit.dto.SignInRequest;
import com.ExpenseTracker.SpendLit.dto.SignUpRequest;
import com.ExpenseTracker.SpendLit.entity.Role;
import com.ExpenseTracker.SpendLit.entity.User;
import com.ExpenseTracker.SpendLit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signUp(SignUpRequest signUpRequest) {
        // Create a new User object
        User user = new User();
        user.setEmail(signUpRequest.getEmail()); // Set user email
        user.setFirstName(signUpRequest.getFirstName()); // Set user first name
        user.setLastName(signUpRequest.getLastName()); // Set user last name
        user.setRole(Role.USER); // Assign default USER role
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // Encode and set password

        return userRepository.save(user); // Save the user to the database
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        // Authenticate user using email and password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        // Retrieve user from repository based on email
        var user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        // Generate JWT and refresh token
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        // Prepare JWT authentication response
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt); // Set the JWT token
        jwtAuthenticationResponse.setRefreshToken(refreshToken); // Set the refresh token
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        // Extract the user's email from the provided refresh token
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());

        // Find the user in the repository using the extracted email
        // If the user does not exist, throw an exception
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        // Validate the provided refresh token for the user
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            // Generate a new JWT for the user
            var jwt = jwtService.generateToken(user);

            // Create a new response object to hold the tokens
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            // Set the newly generated JWT in the response
            jwtAuthenticationResponse.setToken(jwt);

            // Set the refresh token back in the response
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());

            // Return the response containing the new JWT and the original refresh token
            return jwtAuthenticationResponse;
        }

        // Return null if the token is invalid or the process fails
        return null;
    }
}