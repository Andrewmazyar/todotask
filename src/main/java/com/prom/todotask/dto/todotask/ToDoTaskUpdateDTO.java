package com.prom.todotask.dto.todotask;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ToDoTaskUpdateDTO {
    private Long id;
    private String description;
    private LocalDate dueDate;
    private Boolean checkMark;
    private LocalDate completionDate;
}
