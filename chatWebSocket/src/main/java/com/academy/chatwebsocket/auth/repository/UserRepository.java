package com.academy.chatwebsocket.auth.repository;

import com.academy.chatwebsocket.auth.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Integer> {
    void deleteByUsername(String name);

    Optional<CustomUser> findUserByEmail(String email);

    Optional<CustomUser> findUserByUsername(String username);

    Optional<CustomUser> findUserById(int id);

}
