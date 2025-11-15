package com.example.todo.service;

import com.example.todo.BaseTest;
import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class TodoServiceTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TodoService todoService;

    @Test
    void crud() {
        UserEntity u = createUser();
        userMapper.insert(u);

        // 1. add
        TodoEntity t = createTodo(u);
        Assertions.assertTrue(todoService.create(t));

        // 2. get
        TodoEntity load = todoService.get(t.getId());
        Assertions.assertNotNull(load);

        // 3. update
        load.setStatus(1);
        load.setDescription("finish");
        Assertions.assertTrue(todoService.update(load));

        TodoEntity updated = todoService.get(t.getId());
        Assertions.assertEquals(load.getDescription(), updated.getDescription());

        // 4. delete
        Assertions.assertTrue(todoService.delete(t.getId()));
        Assertions.assertNull(todoService.get(t.getId()));
    }

    @Test
    void query() {
        UserEntity u = createUser();
        userMapper.insert(u);

        TodoEntity t = createTodo(u);
        todoService.create(t);

        TodoQuery query = new TodoQuery();
        query.setUserId(u.getId());
        query.setStatus(1);
        query.setName("name");
        query.setDueDateFrom(t.getDueDate());
        query.setDueDateTo(t.getDueDate());
        query.setOrderBy("due_date desc");
        List<TodoEntity> list = todoService.query(query);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(t.getId(), list.get(0).getId());
    }
}