package com.academy.chatwebsocket.auth.config.security;

import com.academy.chatwebsocket.auth.services.JWTService.JWTAuthenticationEntryPoint;
import com.academy.chatwebsocket.auth.services.JWTService.JWTAuthenticationFilter;
import lombok.AllArgsConstructor;
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

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class WebSecuriryConfiger {
    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui/**",
            "*/api-docs/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/swagger-ui/index.html#/"
    };
    private CustomUserDetailsService userServices;
    private JWTAuthenticationEntryPoint point;
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         Configuration for HTTP security
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF protection
                .cors(AbstractHttpConfigurer::disable)  // Disable Cross-Origin Resource Sharing (CORS) protection
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))  // Configure authentication entry point
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Set session creation policy to STATELESS
                .authenticationProvider(authenticationProvider())  // Set the authentication provider

                // Add a filter before the UsernamePasswordAuthenticationFilter (This code snippet needs a filter instance to be added here)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test/hello", "/").permitAll()  // Allow unauthenticated access to URLs starting with /auth/
                        .requestMatchers(AUTH_WHITELIST).permitAll()  // Allow unauthenticated access to URLs starting with /auth/
                        .requestMatchers("/auth/**").permitAll()  // Allow unauthenticated access to URLs starting with /auth/
                        .anyRequest().permitAll()
                )  // Require authentication for any other request

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Define an AuthenticationProvider, which is used for user authentication.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userServices);  // Set the UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder());  // Set the password encoder
        return authProvider;
    }


    // Define a method to obtain an AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Define a BCryptPasswordEncoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
