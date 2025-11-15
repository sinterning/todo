package com.example.todo.service;

import com.example.todo.entity.RoleEntity;

public interface RoleService {

    boolean createRole(RoleEntity role);

    RoleEntity getRole(Long id);

    boolean deleteRole(Long id);

    boolean addUserRole(Long roleId, Long userId);

    boolean hasUserRole(Long roleId, Long userId);

    boolean deleteUserRole(Long roleId, Long userId);

    RoleEntity getByName(String name);
}
