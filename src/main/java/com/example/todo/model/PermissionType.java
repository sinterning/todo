package com.example.todo.model;

import lombok.Getter;

@Getter
public enum PermissionType {
    VIEW(1),
    EDIT(2);

    private final int code;

    PermissionType(int code) {
        this.code = code;
    }

    public static PermissionType fromCode(int code) {
        for (PermissionType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
