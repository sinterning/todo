package com.example.todo.mapper;

import com.example.todo.BaseTest;
import com.example.todo.entity.PermissionEntity;
import com.example.todo.entity.RoleEntity;
import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PermissionMapperTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private TodoMapper todoMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    void crud() {
        UserEntity u = createUser();
        userMapper.insert(u);

        RoleEntity r = createRole();
        roleMapper.insert(r);

        TodoEntity t = createTodo(u);
        todoMapper.insert(t);
        //1. add
        PermissionEntity rp = createPermission(r, t);
        Assertions.assertEquals(1, permissionMapper.insert(rp));
        Assertions.assertNotNull(rp.getId());

        PermissionEntity up = createPermission(u, t);
        Assertions.assertEquals(1, permissionMapper.insert(up));
        Assertions.assertNotNull(rp.getId());

        //2. query
        PermissionEntity loadRp = permissionMapper.selectPermission(rp.getSubjectType(),
                rp.getSubjectId(), rp.getTodoId());
        Assertions.assertNotNull(loadRp);

        // 3. delete
        Assertions.assertEquals(1, permissionMapper.delete(rp.getSubjectType(),
                rp.getSubjectId(), rp.getTodoId()));
        Assertions.assertEquals(1, permissionMapper.deleteByTodo(t.getId()));

        permissionMapper.insert(rp);
        Assertions.assertEquals(1, permissionMapper.deleteByRole(r.getId()));
    }

    @Test
    void userPermission() {
        UserEntity u = createUser();
        userMapper.insert(u);

        RoleEntity r = createRole();
        roleMapper.insert(r);
        userRoleMapper.insert(r.getId(), u.getId());

        TodoEntity t = createTodo(u);
        todoMapper.insert(t);

        PermissionEntity p1 = createPermission(u, t);
        permissionMapper.insert(p1);
        PermissionEntity p2 = createPermission(r, t);
        permissionMapper.insert(p2);

        Assertions.assertEquals(2, permissionMapper.selectUserPermission(u.getId(), t.getId()).size());
    }
}
