package com.academy.chatwebsocket.auth.authDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @Email(message = "Email required")
    private String email;

    @Size(min = 8, message = "Password required")
    private String password;
}
