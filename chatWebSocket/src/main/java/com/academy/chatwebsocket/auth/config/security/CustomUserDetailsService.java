package com.academy.chatwebsocket.auth.config.security;

import com.academy.chatwebsocket.auth.entity.CustomUser;
import com.academy.chatwebsocket.auth.exception.errors.UsernameNotFoundException;
import com.academy.chatwebsocket.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser byEmail = userRepository.findUserByEmail(username).orElseThrow(UsernameNotFoundException::new);
        return byEmail;
    }
}
