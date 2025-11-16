package com.example.todo.controller;

import com.example.todo.entity.RoleEntity;
import com.example.todo.model.ApiResponse;
import com.example.todo.service.RoleService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ApiResponse<Long> create(@RequestParam String roleName) {
        RoleEntity role = roleService.getByName(roleName);
        if (role != null) {
            return ApiResponse.error("role already exist");
        }
        role = new RoleEntity();
        role.setName(roleName);
        if (roleService.createRole(role)) {
            return ApiResponse.success(role.getId());
        } else {
            return ApiResponse.error("Failed to create role");
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable @Positive Long id) {
        RoleEntity old = roleService.getRole(id);
        if (old == null) {
            return ApiResponse.error("role not exist");
        }
        if (roleService.deleteRole(id)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error("Failed to delete role");
        }
    }

    @PutMapping("/{roleId}/users/{userId}")
    public ApiResponse<Void> addUserRole(@PathVariable @Positive Long roleId,
                                         @PathVariable @Positive Long userId) {
        if (roleService.hasUserRole(roleId, userId)) {
            return ApiResponse.error("use already has role");
        }
        if (roleService.addUserRole(roleId, userId)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error("Failed to add role for user");
        }
    }

    @DeleteMapping("/{roleId}/users/{userId}")
    public ApiResponse<Void> deleteUserRole(@PathVariable @Positive Long roleId,
                                            @PathVariable @Positive Long userId) {
        if (!roleService.hasUserRole(roleId, userId)) {
            return ApiResponse.error("user don't has role");
        }
        if (roleService.deleteUserRole(roleId, userId)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error("Failed to remove role for user");
        }
    }
}
