package com.prom.todotask.security;

import com.prom.todotask.entity.User;

public interface AuthenticationService {
    void register(String username, String password);

    User login(String login, String password);
}
