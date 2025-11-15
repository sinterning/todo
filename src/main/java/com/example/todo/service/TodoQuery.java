package com.example.todo.service;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class TodoQuery implements Serializable {
    private Long userId;
    private String name;
    private Integer status;
    private LocalDate dueDateFrom;
    private LocalDate dueDateTo;

    private String orderBy;
}
