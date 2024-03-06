package com.prom.todotask.service.impl;

import com.prom.todotask.dto.user.UserDTO;
import com.prom.todotask.entity.User;
import com.prom.todotask.repository.UserRepository;
import com.prom.todotask.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public UserDTO getByUsername(String username) {
        var user = userRepository.findUserByUsername(username);
        return new UserDTO(user.getId(), user.getUsername());
    }

    @Override
    public boolean isUserExist(String username) {
        return userRepository.existsUserByUsername(username);
    }
}
