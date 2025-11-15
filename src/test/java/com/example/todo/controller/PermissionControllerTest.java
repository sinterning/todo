package com.example.todo.controller;

import com.example.todo.BaseTest;
import com.example.todo.entity.RoleEntity;
import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.model.PermissionType;
import com.example.todo.service.RoleService;
import com.example.todo.service.TodoService;
import com.example.todo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PermissionControllerTest extends BaseTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TodoService todoService;

    @Autowired
    private PermissionController permissionController;

    @Test
    void user() {
        UserEntity u = createUser();
        u = userService.register(u.getEmail(), u.getPassword());

        TodoEntity t = createTodo(u);
        todoService.create(t);
        //add
        Assertions.assertTrue(permissionController.addUserPermission(
                u.getId(), t.getId(), PermissionType.VIEW).isSuccess());
        Assertions.assertFalse(permissionController.addUserPermission(
                u.getId(), t.getId(), PermissionType.VIEW).isSuccess());

        //remove
        Assertions.assertTrue(permissionController.removeUserPermission(
                u.getId(), t.getId()).isSuccess());
        Assertions.assertFalse(permissionController.removeUserPermission(
                u.getId(), t.getId()).isSuccess());
    }

    @Test
    void role() {
        UserEntity u = createUser();
        u = userService.register(u.getEmail(), u.getPassword());

        RoleEntity r=createRole();
        roleService.createRole(r);

        TodoEntity t = createTodo(u);
        todoService.create(t);
        //add
        Assertions.assertTrue(permissionController.addRolePermission(
                r.getId(), r.getId(), PermissionType.VIEW).isSuccess());
        Assertions.assertFalse(permissionController.addRolePermission(
                r.getId(), t.getId(), PermissionType.VIEW).isSuccess());

        //remove
        Assertions.assertTrue(permissionController.removeUserPermission(
                u.getId(), t.getId()).isSuccess());
        Assertions.assertFalse(permissionController.removeUserPermission(
                u.getId(), t.getId()).isSuccess());
    }

}
