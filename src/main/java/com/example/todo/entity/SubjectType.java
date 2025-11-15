package com.example.todo.entity;

import lombok.Getter;

@Getter
public enum SubjectType {
    ROLE(1),
    USER(2);
    private final int code;

    SubjectType(int code) {
        this.code = code;
    }
}
