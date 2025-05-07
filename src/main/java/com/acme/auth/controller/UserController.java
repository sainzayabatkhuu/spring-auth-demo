package com.acme.auth.controller;

import com.acme.auth.dto.UserRequest;
import com.acme.auth.entity.User;
import com.acme.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }


    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        this.userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
