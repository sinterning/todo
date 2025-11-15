package com.example.todo.service;

import com.example.todo.BaseTest;
import com.example.todo.entity.RoleEntity;
import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.mapper.RoleMapper;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.mapper.UserMapper;
import com.example.todo.mapper.UserRoleMapper;
import com.example.todo.model.PermissionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PermissionServiceTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private TodoMapper todoMapper;

    @Autowired
    private PermissionService permissionService;

    @Test
    void user() {
        UserEntity u = createUser();
        userMapper.insert(u);

        TodoEntity t = createTodo(u);
        todoMapper.insert(t);

        Assertions.assertTrue(permissionService.addUserPermission(u.getId(), t.getId(), PermissionType.VIEW));
        Assertions.assertNotNull(permissionService.getUserPermission(u.getId(), t.getId()));
        Assertions.assertTrue(permissionService.removeUserPermission(u.getId(), t.getId()));
    }

    @Test
    void role() {
        UserEntity u = createUser();
        userMapper.insert(u);

        RoleEntity r = createRole();
        roleMapper.insert(r);

        TodoEntity t = createTodo(u);
        todoMapper.insert(t);

        Assertions.assertTrue(permissionService.addRolePermission(r.getId(), t.getId(), PermissionType.VIEW));
        Assertions.assertNotNull(permissionService.getRolePermission(r.getId(), t.getId()));
        Assertions.assertTrue(permissionService.removeRolePermission(r.getId(), t.getId()));
    }

    @Test
    void userAll(){
        UserEntity u = createUser();
        userMapper.insert(u);

        RoleEntity r = createRole();
        roleMapper.insert(r);
        userRoleMapper.insert(r.getId(), u.getId());

        TodoEntity t = createTodo(u);
        todoMapper.insert(t);

        permissionService.addUserPermission(u.getId(), t.getId(), PermissionType.VIEW);
        permissionService.addRolePermission(r.getId(), t.getId(), PermissionType.EDIT);

        Assertions.assertEquals(PermissionType.EDIT, permissionService.getPermission(u.getId(), t.getId()));
    }

}