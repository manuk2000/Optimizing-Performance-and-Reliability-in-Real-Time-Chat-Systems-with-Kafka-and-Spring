package com.academy.chatwebsocket.auth.exception.errors;

import com.academy.chatwebsocket.auth.entity.CustomUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(CustomUser customUser) {

    }

    public UserNotFoundException() {
        super("User Already Exists");
    }
}
