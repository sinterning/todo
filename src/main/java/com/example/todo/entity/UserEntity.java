package com.example.todo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserEntity {
    private Long id;
    private String password;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}