package com.prom.todotask.service.interfaces;

import com.prom.todotask.dto.todotask.ToDoTaskCreateDTO;
import com.prom.todotask.dto.todotask.ToDoTaskDTO;
import com.prom.todotask.dto.todotask.ToDoTaskUpdateDTO;

import java.util.List;

public interface ToDoTaskService {
    ToDoTaskDTO create(ToDoTaskCreateDTO createDTO);
    ToDoTaskDTO update(ToDoTaskUpdateDTO updateDTO);
    List<ToDoTaskDTO> getAll();
    List<ToDoTaskDTO> findAllByUserId(Long userId);
    Boolean delete(Long id);
}
