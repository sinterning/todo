package com.example.todo.model.todo;

import lombok.Getter;

@Getter
public enum TodoStatus {
    NOT_STARTED(1),
    IN_PROGRESS(2),
    COMPLETED(3);

    private final int code;

    TodoStatus(int code) {
        this.code = code;
    }

    public static TodoStatus fromCode(int code) {
        for (TodoStatus status : values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }
}
