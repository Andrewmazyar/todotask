package com.prom.todotask.service.interfaces;

import com.prom.todotask.dto.user.UserDTO;
import com.prom.todotask.entity.User;

public interface UserService {
    void save(User user);
    User findByUsername(String username);

    UserDTO getByUsername(String username);

    boolean isUserExist(String username);
}
