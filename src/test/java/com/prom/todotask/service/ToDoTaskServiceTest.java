package com.prom.todotask.service;

import com.prom.todotask.dto.todotask.ToDoTaskCreateDTO;
import com.prom.todotask.dto.todotask.ToDoTaskDTO;
import com.prom.todotask.dto.todotask.ToDoTaskUpdateDTO;
import com.prom.todotask.entity.ToDoTask;
import com.prom.todotask.exception.BadRequest400Exception;
import com.prom.todotask.mapper.ToDoTaskMapper;
import com.prom.todotask.repository.ToDoTaskRepository;
import com.prom.todotask.service.impl.ToDoTaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ToDoTaskServiceTest {
    @Mock
    private ToDoTaskRepository toDoTaskRepository;

    @Mock
    private ToDoTaskMapper toDoTaskMapper;

    @InjectMocks
    private ToDoTaskServiceImpl toDoTaskService;

    @Test
    public void testCreate() {
        ToDoTaskCreateDTO createDTO = new ToDoTaskCreateDTO();
        ToDoTask task = new ToDoTask();
        ToDoTask savedTask = new ToDoTask();
        ToDoTaskDTO expectedDTO = new ToDoTaskDTO();

        when(toDoTaskMapper.fromCreateDTO(createDTO)).thenReturn(task);
        when(toDoTaskRepository.save(task)).thenReturn(savedTask);
        when(toDoTaskMapper.toDTO(savedTask)).thenReturn(expectedDTO);

        ToDoTaskDTO result = toDoTaskService.create(createDTO);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(toDoTaskRepository, times(1)).save(task);
    }

    @Test
    public void testUpdate_ExistingTask() {
        ToDoTaskUpdateDTO updateDTO = new ToDoTaskUpdateDTO();
        updateDTO.setId(1L);
        ToDoTask storedTask = new ToDoTask();
        ToDoTask updatedTask = new ToDoTask();
        ToDoTaskDTO expectedDTO = new ToDoTaskDTO();

        when(toDoTaskRepository.findById(updateDTO.getId())).thenReturn(Optional.of(storedTask));
        when(toDoTaskMapper.fromUpdateDTO(updateDTO)).thenReturn(updatedTask);
        when(toDoTaskRepository.save(updatedTask)).thenReturn(updatedTask);
        when(toDoTaskMapper.toDTO(updatedTask)).thenReturn(expectedDTO);

        ToDoTaskDTO result = toDoTaskService.update(updateDTO);

        assertNotNull(result);
        assertEquals(expectedDTO, result);
        verify(toDoTaskRepository, times(1)).save(updatedTask);
    }

    @Test
    public void testUpdate_NonExistingTask() {
        ToDoTaskUpdateDTO updateDTO = new ToDoTaskUpdateDTO();
        updateDTO.setId(1L);

        when(toDoTaskRepository.findById(updateDTO.getId())).thenReturn(Optional.empty());

        assertThrows(BadRequest400Exception.class, () -> {
            toDoTaskService.update(updateDTO);
        });
    }

    @Test
    public void testGetAll() {
        List<ToDoTask> tasks = new ArrayList<>();
        tasks.add(new ToDoTask());
        tasks.add(new ToDoTask());

        when(toDoTaskRepository.findAll()).thenReturn(tasks);
        when(toDoTaskMapper.toDTO(any(ToDoTask.class))).thenReturn(new ToDoTaskDTO());

        List<ToDoTaskDTO> result = toDoTaskService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testDelete_ExistingTask() {
        Long taskId = 1L;
        ToDoTask task = new ToDoTask();

        when(toDoTaskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertTrue(toDoTaskService.delete(taskId));
        verify(toDoTaskRepository, times(1)).delete(task);
    }

    @Test
    public void testDelete_NonExistingTask() {
        Long taskId = 1L;

        when(toDoTaskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertFalse(toDoTaskService.delete(taskId));
        verify(toDoTaskRepository, never()).delete(any());
    }

    @Test
    public void testFindAllByUserId() {
        // Arrange
        Long userId = 1L;
        ToDoTask task1 = new ToDoTask();
        task1.setId(1L);
        task1.setUserId(userId);
        ToDoTask task2 = new ToDoTask();
        task2.setId(2L);
        task2.setUserId(userId);

        List<ToDoTask> tasks = Arrays.asList(task1, task2);
        when(toDoTaskRepository.findAllByUserId(userId)).thenReturn(tasks);

        ToDoTaskDTO taskDTO1 = new ToDoTaskDTO();
        taskDTO1.setId(1L);
        ToDoTaskDTO taskDTO2 = new ToDoTaskDTO();
        taskDTO2.setId(2L);

        when(toDoTaskMapper.toDTO(task1)).thenReturn(taskDTO1);
        when(toDoTaskMapper.toDTO(task2)).thenReturn(taskDTO2);

        // Act
        List<ToDoTaskDTO> result = toDoTaskService.findAllByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(taskDTO1.getId(), result.get(0).getId());
        assertEquals(taskDTO2.getId(), result.get(1).getId());

        // Verify that repository method was called with correct userId
        verify(toDoTaskRepository, times(1)).findAllByUserId(userId);
    }
}
