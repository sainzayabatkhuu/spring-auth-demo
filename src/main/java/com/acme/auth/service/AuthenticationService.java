package com.acme.auth.service;

import com.acme.auth.config.JwtService;
import com.acme.auth.dto.AuthenticationRequest;
import com.acme.auth.dto.AuthenticationResponse;
import com.acme.auth.dto.RegisterRequest;
import com.acme.auth.entity.Role;
import com.acme.auth.entity.User;
import com.acme.auth.exception.UserAlreadyExistException;
import com.acme.auth.repository.RoleRepository;
import com.acme.auth.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService) {
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {
        Role role = roleRepository.findByName(request.role);
        Optional<User> existingUserEmail = userRepository.findByEmail(request.email);
        if(existingUserEmail.isPresent()){
            throw new UserAlreadyExistException("User with email " + request.email + " already exists");
        }

        Optional<User> existingUsername = userRepository.findByUsername(request.username);
        if(existingUsername.isPresent()){
            throw new UserAlreadyExistException("User with username " + request.username + " already exists");
        }

        User user = new User();
        user.username = request.username;
        user.password = passwordEncoder.encode(request.password);
        user.roles = Collections.singleton(role);
        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userRepository.findByUsernameOrEmail(request.username(), request.username())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
