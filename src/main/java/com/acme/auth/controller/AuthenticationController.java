package com.acme.auth.controller;

import com.acme.auth.dto.AuthenticationRequest;
import com.acme.auth.dto.AuthenticationResponse;
import com.acme.auth.dto.RegisterRequest;
import com.acme.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    /**
     * It will register user as student width role that is name ROLE_STUDENT.
     *
     * @param request as RegisterRequest
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> register(
            @Valid @RequestBody final RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    /**
     * This will receive login request with username or email and password.
     *
     * @param request as AuthenticationRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
