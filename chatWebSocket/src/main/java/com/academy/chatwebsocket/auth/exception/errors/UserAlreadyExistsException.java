package com.academy.chatwebsocket.auth.exception.errors;

import com.academy.chatwebsocket.auth.entity.CustomUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(CustomUser customUser) {

    }

    public UserAlreadyExistsException() {
        super("User Already Exists");
    }
}
