package com.example.todo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PermissionEntity {
    private Long id;
    private Long todoId;
    private Integer subjectType;
    private Long subjectId;
    private Integer permissionType;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}