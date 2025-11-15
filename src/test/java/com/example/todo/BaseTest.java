package com.example.todo;

import com.example.todo.entity.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@Rollback
public class BaseTest {

    protected UserEntity createUser() {
        UserEntity u = new UserEntity();
        u.setPassword("123456");
        u.setEmail("tom@example.com");
        return u;
    }

    protected TodoEntity createTodo(UserEntity u) {
        TodoEntity t = new TodoEntity();
        t.setName("to do name");
        t.setUserId(u.getId());
        t.setStatus(1);
        t.setDueDate(LocalDateTime.now().plusDays(3).toLocalDate());
        t.setDescription("start");
        return t;
    }

    protected RoleEntity createRole() {
        RoleEntity role = new RoleEntity();
        role.setName("role_1");
        return role;
    }

    protected PermissionEntity createPermission(UserEntity u, TodoEntity t) {
        return createPermission(SubjectType.USER, u.getId(), t.getId());
    }

    protected PermissionEntity createPermission(RoleEntity r, TodoEntity t) {
        return createPermission(SubjectType.ROLE, r.getId(), t.getId());
    }

    private PermissionEntity createPermission(SubjectType subjectType, Long subjectId, Long todoId) {
        PermissionEntity p = new PermissionEntity();
        p.setSubjectType(subjectType.getCode());
        p.setSubjectId(subjectId);
        p.setTodoId(todoId);
        p.setPermissionType(1);
        return p;
    }
}
