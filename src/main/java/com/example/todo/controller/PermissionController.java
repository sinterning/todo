package com.example.todo.controller;

import com.example.todo.model.ApiResponse;
import com.example.todo.model.PermissionType;
import com.example.todo.service.PermissionService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /* 1. 给用户加权限 */
    @PostMapping("/users/{userId}/todos/{todoId}")
    public ApiResponse<Void> addUserPermission(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long todoId,
            @RequestParam @NotNull PermissionType type) {
        if (permissionService.getUserPermission(userId, todoId) != null) {
            return ApiResponse.error("user permission already exist");
        }
        if (permissionService.addUserPermission(userId, todoId, type)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error("Failed to add user permission");
        }
    }

    /* 2. 给角色加权限 */
    @PostMapping("/roles/{roleId}/todos/{todoId}")
    public ApiResponse<Void> addRolePermission(
            @PathVariable @Positive Long roleId,
            @PathVariable @Positive Long todoId,
            @RequestParam @NotNull PermissionType type) {
        if (permissionService.getRolePermission(roleId, todoId) != null) {
            return ApiResponse.error("role permission already exist");
        }
        if (permissionService.addRolePermission(roleId, todoId, type)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error("Failed to add role permission");
        }
    }

    /* 3. 移除用户权限 */
    @DeleteMapping("/users/{userId}/todos/{todoId}")
    public ApiResponse<Void> removeUserPermission(
            @PathVariable @Positive Long userId,
            @PathVariable @Positive Long todoId) {
        if (permissionService.getUserPermission(userId, todoId) == null) {
            return ApiResponse.error("user permission not exist");
        }
        if (permissionService.removeUserPermission(userId, todoId)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error("Failed to remove user permission");
        }
    }

    /* 4. 移除角色权限 */
    @DeleteMapping("/roles/{roleId}/todos/{todoId}")
    public ApiResponse<Void> removeRolePermission(
            @PathVariable @Positive Long roleId,
            @PathVariable @Positive Long todoId) {
        if (permissionService.getRolePermission(roleId, todoId) == null) {
            return ApiResponse.error("role permission not exist");
        }
        if (permissionService.removeRolePermission(roleId, todoId)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error("Failed to remove role permission");
        }
    }
}
