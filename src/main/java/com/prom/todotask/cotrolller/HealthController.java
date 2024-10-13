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
@RequestMapping
public class HealthController {
        @GetMapping("/")
        public ResponseEntity<List<ToDoTaskDTO>> health() {
            return new ResponseEntity<>(HttpStatus.OK);
        }

    @GetMapping("/health")
    public ResponseEntity<List<ToDoTaskDTO>> withHealthPath() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
