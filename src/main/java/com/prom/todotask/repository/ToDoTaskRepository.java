package com.prom.todotask.repository;

import com.prom.todotask.entity.ToDoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoTaskRepository extends JpaRepository<ToDoTask, Long> {
    List<ToDoTask> findAllByUserId(Long userId);
}
