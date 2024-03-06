package com.prom.todotask.repository;

import com.prom.todotask.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = User.builder()
                .username("john.doe")
                .password("password")
                .build();

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    public void testFindById() {
        User user = User.builder()
                .username("jane.doe")
                .password("password")
                .build();
        User savedUser = userRepository.save(user);

        Optional<User> foundUserOptional = userRepository.findById(savedUser.getId());

        assertTrue(foundUserOptional.isPresent());
        User foundUser = foundUserOptional.get();
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals(savedUser.getUsername(), foundUser.getUsername());
        assertEquals(savedUser.getPassword(), foundUser.getPassword());
    }

    @Test
    public void testFindByUsername() {
        User user = User.builder()
                .username("Noah")
                .password("password")
                .build();
        userRepository.save(user);

        User storedUser = userRepository.findUserByUsername("Noah");

        assertEquals(user.getUsername(), storedUser.getUsername());
        assertEquals(user.getPassword(), storedUser.getPassword());
    }

    @Test
    public void testFindByNonExistingUsername() {
        User storedUser = userRepository.findUserByUsername("who are you");

        assertNull(storedUser);
    }

    @Test
    @DirtiesContext
    public void testDeleteUser() {
        User user = User.builder()
                .username("bob.johnson")
                .password("password")
                .build();
        User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        Optional<User> foundUserOptional = userRepository.findById(savedUser.getId());
        assertTrue(foundUserOptional.isEmpty());
    }
}
