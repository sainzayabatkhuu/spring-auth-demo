package com.acme.auth.repository;

import com.acme.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);
    Optional<User> findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);
}
