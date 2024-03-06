package com.prom.todotask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prom.todotask.dto.todotask.ToDoTaskCreateDTO;
import com.prom.todotask.dto.todotask.ToDoTaskDTO;
import com.prom.todotask.dto.todotask.ToDoTaskUpdateDTO;
import com.prom.todotask.service.interfaces.ToDoTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ToDoTaskControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @MockBean
    private ToDoTaskService toDoTaskService;

    @BeforeEach
    void printApplicationContext() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Arrays.stream(webApplicationContext.getBeanDefinitionNames())
                .map(name -> webApplicationContext.getBean(name).getClass().getName())
                .sorted()
                .forEach(System.out::println);
    }

    @Test
    public void testCreate() throws Exception {
        ToDoTaskCreateDTO createDTO = new ToDoTaskCreateDTO();
        createDTO.setDescription("Task 1");

        ToDoTaskDTO createdTask = new ToDoTaskDTO();
        createdTask.setId(1L);
        createdTask.setDescription("Task 1");

        when(toDoTaskService.create(any(ToDoTaskCreateDTO.class))).thenReturn(createdTask);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/to-do-task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        ToDoTaskUpdateDTO updateDTO = new ToDoTaskUpdateDTO();
        updateDTO.setId(1L);
        updateDTO.setDescription("Updated Task");

        ToDoTaskDTO updatedTask = new ToDoTaskDTO();
        updatedTask.setId(1L);
        updatedTask.setDescription("Updated Task");

        when(toDoTaskService.update(any(ToDoTaskUpdateDTO.class))).thenReturn(updatedTask);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/to-do-task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAll() throws Exception {
        ToDoTaskDTO task = new ToDoTaskDTO();
        task.setId(1L);
        task.setDescription("Task 1");

        List<ToDoTaskDTO> tasks = Collections.singletonList(task);

        when(toDoTaskService.getAll()).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/to-do-task")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetById() throws Exception {
        Long userId = 1L;
        ToDoTaskDTO task = new ToDoTaskDTO();
        task.setId(1L);
        task.setDescription("Task 1");

        List<ToDoTaskDTO> tasks = Collections.singletonList(task);

        when(toDoTaskService.findAllByUserId(userId)).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/to-do-task/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        Long taskId = 1L;

        when(toDoTaskService.delete(taskId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/to-do-task/{id}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
