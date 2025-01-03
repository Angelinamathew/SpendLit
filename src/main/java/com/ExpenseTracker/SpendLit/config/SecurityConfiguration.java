package com.ExpenseTracker.SpendLit.config;

import com.ExpenseTracker.SpendLit.entity.Role;
import com.ExpenseTracker.SpendLit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Enables Spring Security
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Custom JWT authentication filter
    private final UserService userService; // Service for user details retrieval

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection for stateless APIs
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/auth/**").permitAll() // Publicly accessible endpoints
                        .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name()) // Admin role required
                        .requestMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name()) // User role required
                        .anyRequest().authenticated()) // All other requests require authentication
                .sessionManagement(manager ->
                        manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Enforce stateless sessions
                .authenticationProvider(authenticationProvider()) // Sets custom authentication provider
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adds JWT filter

        return http.build(); // Builds and returns the security filter chain
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService()); // Sets UserDetailsService
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Sets password encoder
        return authenticationProvider; // Returns configured authentication provider
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configures BCrypt for password encoding
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // Configures AuthenticationManager
    }
}
