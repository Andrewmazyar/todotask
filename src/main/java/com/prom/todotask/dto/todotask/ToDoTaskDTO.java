package com.prom.todotask.dto.todotask;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoTaskDTO {
    private Long id;
    private String description;
    private LocalDate dueDate;
    private LocalDate creationDate;
    private Boolean checkMark;
    private LocalDate completionDate;
    private Long userId;

}
