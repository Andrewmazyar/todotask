package com.prom.todotask.service.impl;

import com.prom.todotask.dto.todotask.ToDoTaskCreateDTO;
import com.prom.todotask.dto.todotask.ToDoTaskDTO;
import com.prom.todotask.dto.todotask.ToDoTaskUpdateDTO;
import com.prom.todotask.entity.ToDoTask;
import com.prom.todotask.exception.BadRequest400Exception;
import com.prom.todotask.mapper.ToDoTaskMapper;
import com.prom.todotask.repository.ToDoTaskRepository;
import com.prom.todotask.service.interfaces.ToDoTaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToDoTaskServiceImpl implements ToDoTaskService {

    private final ToDoTaskRepository toDoTaskRepository;
    private final ToDoTaskMapper toDoTaskMapper;

    @Override
    public ToDoTaskDTO create(ToDoTaskCreateDTO createDTO) {
        var task = toDoTaskMapper.fromCreateDTO(createDTO);
        var savedTask = toDoTaskRepository.save(task);
        return toDoTaskMapper.toDTO(savedTask);
    }

    @Override
    public ToDoTaskDTO update(ToDoTaskUpdateDTO updateDTO) {
        var storedTask = toDoTaskRepository.findById(updateDTO.getId());
        ToDoTask task;
        if (storedTask.isPresent()) {
            task = toDoTaskMapper.fromUpdateDTO(updateDTO);
            task.setCreationDate(storedTask.get().getCreationDate());
            task.setUserId(storedTask.get().getUserId());
        } else {
            throw new BadRequest400Exception("To Do Task doesn't exist");
        }
        var updatedTask = toDoTaskRepository.save(task);
        return toDoTaskMapper.toDTO(updatedTask);
    }

    @Override
    public List<ToDoTaskDTO> getAll() {
        return toDoTaskRepository.findAll().stream()
                .map(toDoTaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ToDoTaskDTO> findAllByUserId(Long userId) {
        return toDoTaskRepository.findAllByUserId(userId).stream()
                .map(toDoTaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(Long id) {
        var task = toDoTaskRepository.findById(id);
        if (task.isPresent()) {
            toDoTaskRepository.delete(task.get());
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
