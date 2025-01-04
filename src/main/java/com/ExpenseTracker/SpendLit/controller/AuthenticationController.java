package com.ExpenseTracker.SpendLit.controller;

import com.ExpenseTracker.SpendLit.dto.JwtAuthenticationResponse;
import com.ExpenseTracker.SpendLit.dto.SignInRequest;
import com.ExpenseTracker.SpendLit.dto.SignUpRequest;
import com.ExpenseTracker.SpendLit.entity.User;
import com.ExpenseTracker.SpendLit.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequest signUpRequest){
       // Calls the signup method in the service and returns the created User in the response
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
        // Calls the signIn method from the AuthenticationService and returns the response
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
}
