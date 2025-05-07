package com.acme.auth.repository;

import com.acme.auth.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() {
        // given
        User user = new User();
        user.username = ("john");
        user.email = ("john@example.com");
        user.password = ("secret");
        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByEmail("john@example.com");

        // then
        assertTrue(found.isPresent());
        assertEquals("john", found.get().getUsername());
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.username = ("alice");
        user.email = ("alice@example.com");
        user.password = ("secret");
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("alice");
        assertTrue(found.isPresent());
        assertEquals("alice@example.com", found.get().email);
    }

    @Test
    void testExistsByEmail() {
        User user = new User();
        user.username = ("mike");
        user.email = ("mike@example.com");
        user.password = ("secret");
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("mike@example.com");
        assertTrue(exists);

        boolean notExists = userRepository.existsByEmail("unknown@example.com");
        assertFalse(notExists);
    }

    @Test
    void testFindByUsernameOrEmail() {
        User user = new User();
        user.username = ("emma");
        user.email = ("emma@example.com");
        user.password = ("secret");
        userRepository.save(user);

        Optional<User> foundByUsername = userRepository.findByUsernameOrEmail("emma", "not@found.com");
        assertTrue(foundByUsername.isPresent());

        Optional<User> foundByEmail = userRepository.findByUsernameOrEmail("not_found", "emma@example.com");
        assertTrue(foundByEmail.isPresent());

        Optional<User> notFound = userRepository.findByUsernameOrEmail("x", "y");
        assertFalse(notFound.isPresent());
    }
}
