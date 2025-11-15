package com.example.todo.service;

import com.example.todo.entity.PermissionEntity;
import com.example.todo.model.PermissionType;

public interface PermissionService {

    boolean addUserPermission(Long userId, Long todoId, PermissionType permissionType);

    boolean addRolePermission(Long roleId, Long todoId, PermissionType permissionType);

    boolean removeUserPermission(Long userId, Long todoId);

    boolean removeRolePermission(Long roleId, Long todoId);

    PermissionEntity getUserPermission(Long userId, Long todoId);

    PermissionEntity getRolePermission(Long roleId, Long todoId);

    PermissionType getPermission(Long userId, Long todoId);
}
