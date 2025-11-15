package com.example.todo.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TodoEntity {
    private Long id;
    private String name;
    private Long userId;
    private Integer status;
    private LocalDate dueDate;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}