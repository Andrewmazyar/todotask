package com.prom.todotask.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "to_do_task")
public class ToDoTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "check_mark")
    private Boolean checkMark;
    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "user_id")
    private Long userId;

}
