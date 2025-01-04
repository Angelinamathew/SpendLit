package com.ExpenseTracker.SpendLit.controller.AuthController;

import com.ExpenseTracker.SpendLit.dto.AuthDto.JwtAuthenticationResponse;
import com.ExpenseTracker.SpendLit.dto.AuthDto.RefreshTokenRequest;
import com.ExpenseTracker.SpendLit.dto.AuthDto.SignInRequest;
import com.ExpenseTracker.SpendLit.dto.AuthDto.SignUpRequest;
import com.ExpenseTracker.SpendLit.entity.User;
import com.ExpenseTracker.SpendLit.service.authentication.AuthenticationService;
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

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        // Calls the signIn method from the AuthenticationService and returns the response
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
