package com.prom.todotask.security.impl;

import com.prom.todotask.entity.User;
import com.prom.todotask.exception.BadRequest400Exception;
import com.prom.todotask.exception.Unauthorized401Exception;
import com.prom.todotask.security.AuthenticationService;
import com.prom.todotask.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public void register(String username, String password) {
        if (userService.isUserExist(username)) throw new BadRequest400Exception("Username already in use");
        User user = new User();
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);
        userService.save(user);
    }

    @Override
    public User login(String login, String password) {
        User user = userService.findByUsername(login);
        String encodedPassword = passwordEncoder.encode(password);
        if (user == null || !user.getPassword().equals(encodedPassword)) {
            throw new Unauthorized401Exception("Incorrect username or password!!!");
        }
        return user;
    }
}
