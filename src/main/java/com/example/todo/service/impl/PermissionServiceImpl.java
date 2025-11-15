package com.example.todo.service.impl;

import com.example.todo.entity.PermissionEntity;
import com.example.todo.entity.SubjectType;
import com.example.todo.mapper.PermissionMapper;
import com.example.todo.model.PermissionType;
import com.example.todo.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public boolean addUserPermission(Long userId, Long todoId, PermissionType permissionType) {
        log.info("add {} permission to todo {} for user {}", permissionType, todoId, userId);
        return addPermission(SubjectType.USER, userId, todoId, permissionType);
    }

    @Override
    public boolean addRolePermission(Long roleId, Long todoId, PermissionType permissionType) {
        log.info("add {} permission to todo {} for role {}", permissionType, todoId, roleId);
        return addPermission(SubjectType.ROLE, roleId, todoId, permissionType);
    }

    @Override
    public PermissionEntity getUserPermission(Long userId, Long todoId) {
        log.info("get permission of todo {} for user {}", todoId, userId);
        return permissionMapper.selectPermission(SubjectType.USER.getCode(), userId, todoId);
    }

    @Override
    public PermissionEntity getRolePermission(Long roleId, Long todoId) {
        log.info("get permission of todo {} for role {}", todoId, roleId);
        return permissionMapper.selectPermission(SubjectType.ROLE.getCode(), roleId, todoId);
    }

    @Override
    public boolean removeUserPermission(Long userId, Long todoId) {
        log.info("remove permission of todo {} for user {}", todoId, userId);
        return permissionMapper.delete(SubjectType.USER.getCode(), userId, todoId) == 1;
    }

    @Override
    public boolean removeRolePermission(Long roleId, Long todoId) {
        log.info("remove permission of todo {} for role {}", todoId, roleId);
        return permissionMapper.delete(SubjectType.ROLE.getCode(), roleId, todoId) == 1;
    }

    @Override
    public PermissionType getPermission(Long userId, Long todoId) {
        List<PermissionEntity> result = permissionMapper.selectUserPermission(userId, todoId);
        return result.stream()
                .map(PermissionEntity::getPermissionType)
                .max(Integer::compare)
                .map(PermissionType::fromCode)
                .orElse(null);
    }

    private boolean addPermission(SubjectType subjectType, Long subjectId, Long todoId, PermissionType permissionType) {
        log.info("add permission {} for {} with id {} to todo {}",
                permissionType, subjectType, subjectId, todoId);
        PermissionEntity p = new PermissionEntity();
        p.setSubjectType(subjectType.getCode());
        p.setSubjectId(subjectId);
        p.setTodoId(todoId);
        p.setPermissionType(permissionType.getCode());
        return permissionMapper.insert(p) == 1;
    }
}
