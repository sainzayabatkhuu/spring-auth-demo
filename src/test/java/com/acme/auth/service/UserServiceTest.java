package com.acme.auth.service;

import com.acme.auth.dto.UserRequest;
import com.acme.auth.entity.Role;
import com.acme.auth.entity.User;
import com.acme.auth.repository.RoleRepository;
import com.acme.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // JUnit 5 + Mockito
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    // Helper method to create a mock UserRequest for testing
    private UserRequest createUserRequest(String username, String password) {
        return new UserRequest(username, password);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10); // Define pageable

        User user1 = new User();
        user1.username = ("user1");
        User user2 = new User();
        user2.username = ("user2");

        // Create a Page with mock users
        Page<User> mockPage = new PageImpl<>(List.of(user1, user2));
        when(userRepository.findAll(pageable)).thenReturn(mockPage);

        // Act
        List<User> users = userService.getAllUsers(pageable);

        // Assert
        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    void testCreateUser() {
        // Arrange
        UserRequest userRequest = new UserRequest("john", "password123");

        // Mock the Role and PasswordEncoder behavior
        Role mockRole = new Role();
        mockRole.name = ("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(mockRole);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Return the argument passed to save

        // Act
        User createdUser = userService.createUser(userRequest);

        // Assert
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals("john", capturedUser.getUsername());
        assertEquals("encodedPassword", capturedUser.getPassword());
        assertTrue(capturedUser.is_active);
        assertEquals("test", capturedUser.activation_key);
        assertTrue(capturedUser.roles.contains(mockRole)); // Ensure the role is assigned
    }


}