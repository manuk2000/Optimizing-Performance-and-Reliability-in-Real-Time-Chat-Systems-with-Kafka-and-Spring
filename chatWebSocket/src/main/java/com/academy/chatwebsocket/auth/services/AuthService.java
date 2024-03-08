package com.academy.chatwebsocket.auth.services;

import com.academy.chatwebsocket.auth.authDTO.LoginRequestDTO;
import com.academy.chatwebsocket.auth.authDTO.LoginResponseDTO;
import com.academy.chatwebsocket.auth.authDTO.RegistrationFormDTO;
import com.academy.chatwebsocket.auth.entity.CustomUser;
import com.academy.chatwebsocket.auth.exception.errors.UserAlreadyExistsException;
import com.academy.chatwebsocket.auth.exception.errors.UserNotFoundException;
import com.academy.chatwebsocket.auth.services.JWTService.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserServices userServices;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDTO login(@Valid LoginRequestDTO loginForm) {
        CustomUser customUser = userServices.getUserByEmail(loginForm.getEmail()).get();

        if (customUser.getPassword().equals(passwordEncoder.encode(loginForm.getPassword()))) {
            log.error("Signin : bad email or password.");
            throw new UserNotFoundException("Bad email or password.");
        }

        String accessToken = jwtService.generateAccessToken(customUser.getUsername());
        String refreshToken = jwtService.generateRefreshToken(customUser.getUsername());

        return new LoginResponseDTO(accessToken, refreshToken);
    }

    public CustomUser registration(@Valid RegistrationFormDTO dto) {

        userServices.getUserByEmail(dto.getEmail()).ifPresent(UserAlreadyExistsException::new);
        CustomUser customUser = new CustomUser()
                .setUsername(dto.getName())
                .setEmail(dto.getEmail())
                .setPassword(passwordEncoder.encode(dto.getPassword()));
        return userServices.save(customUser);
    }
}
