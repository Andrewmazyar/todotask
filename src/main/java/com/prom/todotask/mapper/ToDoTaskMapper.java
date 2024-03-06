package com.prom.todotask.mapper;

import com.prom.todotask.dto.todotask.ToDoTaskCreateDTO;
import com.prom.todotask.dto.todotask.ToDoTaskDTO;
import com.prom.todotask.dto.todotask.ToDoTaskUpdateDTO;
import com.prom.todotask.entity.ToDoTask;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ToDoTaskMapper {

    public ToDoTask fromCreateDTO(ToDoTaskCreateDTO createDTO) {
        var toDoTask = new ToDoTask();
        toDoTask.setCheckMark(createDTO.getCheckMark());
        toDoTask.setCreationDate(LocalDate.now());
        toDoTask.setDescription(createDTO.getDescription());
        toDoTask.setDueDate(createDTO.getDueDate());
        toDoTask.setCompletionDate(createDTO.getCompletionDate());
        toDoTask.setUserId(createDTO.getUserId());
        return toDoTask;
    }

    public ToDoTask fromUpdateDTO(ToDoTaskUpdateDTO updateDTO) {
        var toDoTask = new ToDoTask();
        toDoTask.setId(updateDTO.getId());
        toDoTask.setCheckMark(updateDTO.getCheckMark());
        toDoTask.setDescription(updateDTO.getDescription());
        toDoTask.setDueDate(updateDTO.getDueDate());
        toDoTask.setCompletionDate(updateDTO.getCompletionDate());
        return toDoTask;
    }

    public ToDoTaskDTO toDTO(ToDoTask task) {
        return new ToDoTaskDTO(
                task.getId(),
                task.getDescription(),
                task.getDueDate(),
                task.getCreationDate(),
                task.getCheckMark(),
                task.getCompletionDate(),
                task.getUserId()
        );
    }
}
