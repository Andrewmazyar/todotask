package com.prom.todotask.security.impl;

import com.prom.todotask.entity.User;
import com.prom.todotask.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.withUsername;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        UserBuilder userBuilder;
        if (user != null) {
            userBuilder = withUsername(username);
            userBuilder.password(user.getPassword());
        } else {
            throw new UsernameNotFoundException("No such User");
        }
        return userBuilder.build();
    }
}
