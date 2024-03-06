package com.prom.todotask.repository;

import com.prom.todotask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);

    Boolean existsUserByUsername(String username);
}
