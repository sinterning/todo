package com.example.todo.service;

import com.example.todo.BaseTest;
import com.example.todo.entity.RoleEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RoleServiceTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Test
    void role() {
        RoleEntity r = createRole();
        //1 add
        Assertions.assertTrue(roleService.createRole(r));
        //2 get
        Assertions.assertNotNull(roleService.getRole(r.getId()));
        Assertions.assertNotNull(roleService.getByName(r.getName()));
    }

    @Test
    void userRole() {
        UserEntity u = createUser();
        userMapper.insert(u);

        RoleEntity r = createRole();
        roleService.createRole(r);

        //1 add
        Assertions.assertTrue(roleService.addUserRole(r.getId(), u.getId()));

        //2. get
        Assertions.assertTrue(roleService.hasUserRole(r.getId(), u.getId()));

        //3. delete
        Assertions.assertTrue(roleService.deleteUserRole(r.getId(), u.getId()));

        roleService.addUserRole(r.getId(), u.getId());
        Assertions.assertTrue(roleService.deleteRole(r.getId()));
        Assertions.assertFalse(roleService.deleteUserRole(r.getId(), u.getId()));

    }

}