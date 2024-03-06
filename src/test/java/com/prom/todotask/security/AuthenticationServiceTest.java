package com.prom.todotask.security;

import com.prom.todotask.entity.User;
import com.prom.todotask.exception.BadRequest400Exception;
import com.prom.todotask.security.impl.AuthenticationServiceImpl;
import com.prom.todotask.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthenticationServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void testRegister_SuccessfulRegistration() {
        String username = "testUser";
        String password = "password";
        User user = new User();
        user.setUsername(username);

        when(userService.isUserExist(username)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        authenticationService.register(username, password);

        verify(passwordEncoder, times(1)).encode(password);
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    public void testRegister_UsernameAlreadyExists() {
        String username = "existingUser";
        String password = "password";

        when(userService.isUserExist(username)).thenReturn(true);

        assertThrows(BadRequest400Exception.class, () -> {
            authenticationService.register(username, password);
        });
    }

    @Test
    public void testLogin_SuccessfulLogin() {
        String username = "testUser";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userService.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        User loggedInUser = authenticationService.login(username, password);

        assertNotNull(loggedInUser);
        assertEquals(username, loggedInUser.getUsername());
    }

    @Test
    public void testLogin_IncorrectCredentials() {
        String username = "testUser";
        String password = "password";
        User user = new User();
        user.setUsername(username);
        user.setPassword("encodedPassword");

        when(userService.findByUsername(username)).thenReturn(user);
        when(passwordEncoder.encode(password)).thenReturn("wrongEncodedPassword");

        assertThrows(RuntimeException.class, () -> {
            authenticationService.login(username, password);
        });
    }
}
