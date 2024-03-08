package com.academy.chatwebsocket.auth.authDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
}