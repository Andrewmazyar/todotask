package com.prom.todotask.cotrolller;

import com.prom.todotask.dto.todotask.ToDoTaskCreateDTO;
import com.prom.todotask.dto.todotask.ToDoTaskDTO;
import com.prom.todotask.dto.todotask.ToDoTaskUpdateDTO;
import com.prom.todotask.service.interfaces.ToDoTaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/to-do-task")
@AllArgsConstructor
public class ToDoTaskController {

    private final ToDoTaskService toDoTaskService;

    @PostMapping
    public ResponseEntity<ToDoTaskDTO> create(@RequestBody ToDoTaskCreateDTO createDTO) {
        var result = toDoTaskService.create(createDTO);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ToDoTaskDTO> update(@RequestBody ToDoTaskUpdateDTO updateDTO) {
        var result = toDoTaskService.update(updateDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ToDoTaskDTO>> getAll() {
        return new ResponseEntity<>(
                toDoTaskService.getAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ToDoTaskDTO>> getById(@PathVariable("id") Long userId) {
        return new ResponseEntity<>(
                toDoTaskService.findAllByUserId(userId),
                HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(
                toDoTaskService.delete(id)
                        ? HttpStatus.OK
                        : HttpStatus.NO_CONTENT
        );
    }
}
