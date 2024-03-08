package com.academy.chatwebsocket.auth.exception.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class UsernameNotFoundException extends org.springframework.security.core.userdetails.UsernameNotFoundException {
    public UsernameNotFoundException() {
        super("User not found");
    }
}
