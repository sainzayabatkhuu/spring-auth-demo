package com.acme.auth.controller;

import com.acme.auth.dto.UserRequest;
import com.acme.auth.entity.User;
import com.acme.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService; // Mock the UserService

    @Autowired
    private ObjectMapper objectMapper; // To convert objects to JSON

    @BeforeEach
    void setUp() {
        // Set up any common mock data if needed
    }

    private String generateToken() {
        String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"; // Use same key as in your app

        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", "john_doe");

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(keyBytes))
                .compact();
        System.out.println(token);
        return "Bearer " + token;
    }

    @Test
    void testGetAllUsers() throws Exception {
        // Arrange
        User user1 = new User();
        user1.username = ("user1");
        User user2 = new User();
        user2.username = ("user2");

        List<User> users = Arrays.asList(user1, user2);
        when(userService.getAllUsers(any())).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/v1/users").header("Authorization", generateToken())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk()) // Expect 200 OK status
                .andExpect(jsonPath("$[0].username").value("john_doe")); // Check second user's username
    }

    @Test
    void testCreateUser() throws Exception {
        // Arrange
        UserRequest userRequest = new UserRequest("newUser", "password123");

        // Act & Assert
        mockMvc.perform(post("/api/v1/users").header("Authorization", generateToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest))) // Convert the request to JSON
                .andExpect(status().isCreated()); // Expect 201 Created status
    }
}
