package com.academy.chatwebsocket.auth.services;

import com.academy.chatwebsocket.auth.entity.CustomUser;
import com.academy.chatwebsocket.auth.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class UserServices {
    private final UserRepository userRepository;

    public final List<CustomUser> getAllUsers() {
        return userRepository.findAll();
    }

    public CustomUser findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public CustomUser save(CustomUser customUser) {
        return userRepository.save(customUser);
    }

    public Optional<CustomUser> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void deleteUser(Principal principal) {
        userRepository.deleteByUsername(principal.getName());
    }

}
