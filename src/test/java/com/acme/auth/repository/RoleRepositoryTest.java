package com.acme.auth.repository;

import com.acme.auth.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByName() {
        // Given
        Role savedRole = new Role();
        savedRole.name = "ADMIN";
        roleRepository.save(savedRole);

        // When
        Role found = roleRepository.findByName("ADMIN");

        // Then
        assertNotNull(found);
        assertEquals("ADMIN", found.name);
    }

    @Test
    void testFindByName_NotFound() {
        // When
        Role found = roleRepository.findByName("UNKNOWN");

        // Then
        assertNull(found);
    }
}