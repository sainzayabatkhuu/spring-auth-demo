package com.acme.auth.service;

import com.acme.auth.dto.UserRequest;
import com.acme.auth.entity.Role;
import com.acme.auth.entity.User;
import com.acme.auth.repository.RoleRepository;
import com.acme.auth.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }

    public User createUser(UserRequest userRequest) {
        Role role = roleRepository.findByName("ROLE_USER");
        User user = new User();
        user.username = userRequest.username();
        user.password = passwordEncoder.encode(userRequest.password());
        user.activation_key = "test";
        user.is_active = true;
        user.roles = Collections.singleton(role);

        return userRepository.save(user);
    }
}
