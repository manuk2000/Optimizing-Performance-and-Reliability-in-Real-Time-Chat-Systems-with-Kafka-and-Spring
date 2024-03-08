package com.academy.chatwebsocket.auth.authDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class ResetPassword {
    @Email(message = "Email required")
    private String email;

    @Size(min = 8, message = "Password required")
    private String newPassword;
}
