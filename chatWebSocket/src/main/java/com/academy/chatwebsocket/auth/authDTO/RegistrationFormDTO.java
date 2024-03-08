package com.academy.chatwebsocket.auth.authDTO;

import com.academy.chatwebsocket.auth.annotations.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Builder
public class RegistrationFormDTO {
    @NotBlank(message = "Name required")
    private String name;

    @Email(message = "Email required")
    private String email;

    @PasswordMatches(message = "Passwords don't have allowed syntax. Pleas insert password of at least character and number")
    @Size(min = 8, message = "Password required")
    private String password;
}