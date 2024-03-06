package com.prom.todotask.repository;

import com.prom.todotask.entity.ToDoTask;
import com.prom.todotask.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToDoToDoTaskRepositoryTest {
    @Autowired
    private ToDoTaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testuser")
                .password("testpassword")
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void findAllByUserId() {
        ToDoTask task = ToDoTask.builder()
                .description("Sample ToDoTask")
                .dueDate(LocalDate.now().plusDays(1))
                .checkMark(false)
                .creationDate(LocalDate.now())
                .userId(user.getId())
                .build();
        taskRepository.save(task);

        List<ToDoTask> tasks = taskRepository.findAllByUserId(user.getId());
        assertEquals(1, tasks.size());
        ToDoTask savedToDoTask = tasks.get(0);
        assertEquals("Sample ToDoTask", savedToDoTask.getDescription());
        assertEquals(user.getId(), savedToDoTask.getUserId());
    }

    @Test
    void findAllByUserId_NoToDoTasksFound() {
        List<ToDoTask> tasks = taskRepository.findAllByUserId(100L);
        assertEquals(0, tasks.size());
    }
}
