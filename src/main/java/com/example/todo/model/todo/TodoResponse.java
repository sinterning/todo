package com.example.todo.model.todo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TodoResponse {
    private Long id;
    private String name;
    private Long userId;
    private TodoStatus status;
    private LocalDate dueDate;
    private String description;
}
