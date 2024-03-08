package com.academy.chatwebsocket.auth.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private LocalDateTime createdDate;

}
