package com.academy.chatwebsocket.auth.web;

import com.academy.chatwebsocket.auth.DTO.UserDTO;
import com.academy.chatwebsocket.auth.authDTO.LoginRequestDTO;
import com.academy.chatwebsocket.auth.authDTO.LoginResponseDTO;
import com.academy.chatwebsocket.auth.authDTO.RegistrationFormDTO;
import com.academy.chatwebsocket.auth.entity.CustomUser;
import com.academy.chatwebsocket.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
//@Tag(name = "Authorization Controller", description = "This controller for authorization")
public class AuthController {
    private final AuthService authService;
    private final ModelMapper mapper;

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("Login is succesfuly");
        LoginResponseDTO login = authService.login(loginRequestDTO);

        return ResponseEntity.ok(login);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registration(@RequestBody RegistrationFormDTO dto) {
        CustomUser registration = authService.registration(dto);
        log.info("Registration : email {}, name {}", registration.getEmail(), registration.getUsername());
        return ResponseEntity.ok(mapper.map(registration, UserDTO.class));
//        return ResponseEntity.ok(null);
    }
}
