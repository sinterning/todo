package com.example.todo.service.impl;

import com.example.todo.entity.RoleEntity;
import com.example.todo.mapper.PermissionMapper;
import com.example.todo.mapper.RoleMapper;
import com.example.todo.mapper.UserRoleMapper;
import com.example.todo.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final PermissionMapper permissionMapper;

    private final RoleMapper roleMapper;

    private final UserRoleMapper userRoleMapper;

    @Override
    public boolean createRole(RoleEntity role) {
        log.info("add role {}", role.getName());
        return roleMapper.insert(role) == 1;
    }

    @Override
    public RoleEntity getRole(Long id) {
        log.info("get role for id:  {}", id);
        return roleMapper.selectById(id);
    }

    @Override
    public RoleEntity getByName(String name) {
        log.info("get role for name: {}", name);
        return roleMapper.selectByName(name);
    }

    @Override
    @Transactional
    public boolean deleteRole(Long id) {
        log.info("remove users for role {}", id);
        userRoleMapper.deleteByRole(id);
        log.info("remove permission for role {}", id);
        permissionMapper.deleteByRole(id);
        log.info("remove role {}", id);
        int count = roleMapper.deleteById(id);
        return count == 1;
    }

    @Override
    public boolean addUserRole(Long roleId, Long userId) {
        log.info("add role {} for user {}", roleId, userId);
        return userRoleMapper.insert(roleId, userId) == 1;
    }

    @Override
    public boolean hasUserRole(Long roleId, Long userId) {
        log.info("check role {} for user {}", roleId, userId);
        return userRoleMapper.select(roleId, userId) > 0;
    }

    @Override
    @Transactional
    public boolean deleteUserRole(Long roleId, Long userId) {
        log.info("remove role {} for user {}", roleId, userId);
        return userRoleMapper.delete(roleId, userId) == 1;
    }
}

