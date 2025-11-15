package com.example.todo.controller;

import com.example.todo.BaseTest;
import com.example.todo.entity.RoleEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.service.RoleService;
import com.example.todo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RoleControllerTest extends BaseTest {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleController roleController;

    @Test
    void role() {
        String roleName = "role_1";
        //1. create
        Assertions.assertTrue(roleController.create(roleName).isSuccess());
        Assertions.assertFalse(roleController.create(roleName).isSuccess());

        RoleEntity r = roleService.getByName(roleName);
        Assertions.assertTrue(roleController.delete(r.getId()).isSuccess());
        Assertions.assertFalse(roleController.delete(r.getId()).isSuccess());
    }

    @Test
    void userRole() {
        UserEntity u = createUser();
        u = userService.register(u.getEmail(), u.getPassword());

        RoleEntity r = createRole();
        roleService.createRole(r);

        //1. add
        Assertions.assertTrue(roleController.addUserRole(r.getId(), u.getId()).isSuccess());
        Assertions.assertFalse(roleController.addUserRole(r.getId(), u.getId()).isSuccess());

        //2. remove
        Assertions.assertTrue(roleController.deleteUserRole(r.getId(), u.getId()).isSuccess());
        Assertions.assertFalse(roleController.deleteUserRole(r.getId(), u.getId()).isSuccess());
    }

}
