package com.example.todo.mapper;

import com.example.todo.BaseTest;
import com.example.todo.entity.RoleEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RoleMapperTest extends BaseTest {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    void crud() {
        // 1. add
        RoleEntity r = createRole();
        Assertions.assertEquals(1, roleMapper.insert(r));
        Assertions.assertNotNull(r.getId());

        // 2. query
        RoleEntity load = roleMapper.selectById(r.getId());
        Assertions.assertNotNull(load);
        Assertions.assertEquals(r.getName(), load.getName());
        Assertions.assertNotNull(load.getCreateTime());
        Assertions.assertNotNull(load.getUpdateTime());

        Assertions.assertNotNull(roleMapper.selectByName(r.getName()));

        // 3. update
        load.setName("role_2");
        Assertions.assertEquals(1, roleMapper.updateById(load));
        RoleEntity updated = roleMapper.selectById(r.getId());
        Assertions.assertEquals(load.getName(), updated.getName());

        // 4. delete
        Assertions.assertEquals(1, roleMapper.deleteById(r.getId()));
        Assertions.assertNull(roleMapper.selectById(r.getId()));
    }
}