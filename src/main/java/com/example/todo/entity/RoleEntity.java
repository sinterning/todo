package com.example.todo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleEntity {
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
