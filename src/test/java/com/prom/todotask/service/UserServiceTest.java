package com.prom.todotask.service;

import com.prom.todotask.dto.user.UserDTO;
import com.prom.todotask.entity.User;
import com.prom.todotask.repository.UserRepository;
import com.prom.todotask.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSave() {
        User user = new User();

        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindByUsername() {
        String username = "testUser";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        when(userRepository.findUserByUsername(username)).thenReturn(expectedUser);

        User result = userService.findByUsername(username);

        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    public void testGetByUsername() {
        String username = "testUser";
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        when(userRepository.findUserByUsername(username)).thenReturn(user);

        UserDTO result = userService.getByUsername(username);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    public void testIsUserExist_UserExists() {
        String username = "testUser";

        when(userRepository.existsUserByUsername(username)).thenReturn(true);

        assertTrue(userService.isUserExist(username));
    }

    @Test
    public void testIsUserExist_UserDoesNotExist() {
        String username = "testUser";

        when(userRepository.existsUserByUsername(username)).thenReturn(false);

        assertFalse(userService.isUserExist(username));
    }
}
