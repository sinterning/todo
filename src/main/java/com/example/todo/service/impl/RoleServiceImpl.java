package com.example.todo.service.impl;

import com.example.todo.entity.RoleEntity;
import com.example.todo.mapper.PermissionMapper;
import com.example.todo.mapper.RoleMapper;
import com.example.todo.mapper.UserRoleMapper;
import com.example.todo.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public boolean createRole(RoleEntity role) {
        log.info("add role {}", role.getName());
        return roleMapper.insert(role) == 1;
    }

    @Override
    public RoleEntity getRole(Long id) {
        log.info("get role for {}", id);
        return roleMapper.selectById(id);
    }

    @Override
    public RoleEntity getByName(String name) {
        return roleMapper.selectByName(name);
    }

    @Override
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
        return userRoleMapper.select(roleId, userId)>0;
    }

    @Override
    public boolean deleteUserRole(Long roleId, Long userId) {
        log.info("remove role {} for user {}", roleId, userId);
        return userRoleMapper.delete(roleId, userId) == 1;
    }
}

