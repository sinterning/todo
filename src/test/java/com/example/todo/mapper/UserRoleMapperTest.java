package com.example.todo.mapper;

import com.example.todo.BaseTest;
import com.example.todo.entity.RoleEntity;
import com.example.todo.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRoleMapperTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    void crud() {
        UserEntity u = createUser();
        userMapper.insert(u);

        RoleEntity r = createRole();
        roleMapper.insert(r);
        // 1. add
        Assertions.assertEquals(1, userRoleMapper.insert(r.getId(), u.getId()));

        //2. select
        Assertions.assertEquals(1, userRoleMapper.select(r.getId(), u.getId()));

        //3. delete
        Assertions.assertEquals(1, userRoleMapper.delete(r.getId(), u.getId()));

        userRoleMapper.insert(r.getId(), u.getId());
        Assertions.assertEquals(1, userRoleMapper.deleteByRole(r.getId()));
    }
}