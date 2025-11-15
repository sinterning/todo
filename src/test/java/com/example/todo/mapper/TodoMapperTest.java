package com.example.todo.mapper;

import com.example.todo.BaseTest;
import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.service.TodoQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class TodoMapperTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TodoMapper todoMapper;

    @Test
    void crud() {
        UserEntity u = createUser();
        userMapper.insert(u);

        // 1. add
        TodoEntity t = createTodo(u);
        Assertions.assertEquals(1, todoMapper.insert(t));
        Assertions.assertNotNull(t.getId());

        // 2. query
        TodoEntity load = todoMapper.selectById(t.getId());
        Assertions.assertNotNull(load);
        Assertions.assertEquals(t.getUserId(), load.getUserId());
        Assertions.assertEquals(t.getName(), load.getName());
        Assertions.assertEquals(t.getDescription(), load.getDescription());
        Assertions.assertEquals(t.getStatus(), load.getStatus());
        Assertions.assertEquals(t.getDueDate(), load.getDueDate());
        Assertions.assertNotNull(load.getCreateTime());
        Assertions.assertNotNull(load.getUpdateTime());

        // 3. update
        load.setStatus(1);
        load.setDescription("finish");
        Assertions.assertEquals(1, todoMapper.updateById(load));
        TodoEntity updated = todoMapper.selectById(t.getId());
        Assertions.assertEquals(load.getStatus(), updated.getStatus());
        Assertions.assertEquals(load.getDescription(), updated.getDescription());

        // 4. delete
        Assertions.assertEquals(1, todoMapper.deleteById(t.getId()));
        Assertions.assertNull(todoMapper.selectById(t.getId()));
    }

    @Test
    void query() {
        UserEntity u = createUser();
        userMapper.insert(u);

        TodoEntity t = createTodo(u);
        todoMapper.insert(t);

        TodoQuery query = new TodoQuery();
        query.setUserId(u.getId());
        List<TodoEntity> list = todoMapper.selectByQuery(query);
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(t.getId(), list.get(0).getId());

        query.setStatus(1);
        Assertions.assertFalse(todoMapper.selectByQuery(query).isEmpty());
        query.setStatus(2);
        Assertions.assertTrue(todoMapper.selectByQuery(query).isEmpty());

        query.setStatus(null);
        query.setName("name");
        Assertions.assertFalse(todoMapper.selectByQuery(query).isEmpty());
        query.setName("name1");
        Assertions.assertTrue(todoMapper.selectByQuery(query).isEmpty());

        query.setName(null);
        query.setDueDateFrom(t.getDueDate());
        Assertions.assertFalse(todoMapper.selectByQuery(query).isEmpty());
        query.setDueDateFrom(t.getDueDate().plusDays(1));
        Assertions.assertTrue(todoMapper.selectByQuery(query).isEmpty());

        query.setDueDateFrom(null);
        query.setDueDateTo(t.getDueDate());
        Assertions.assertFalse(todoMapper.selectByQuery(query).isEmpty());
        query.setDueDateTo(t.getDueDate().plusDays(-1));
        Assertions.assertTrue(todoMapper.selectByQuery(query).isEmpty());

        query.setDueDateTo(null);
        query.setOrderBy("due_date desc");
        Assertions.assertFalse(todoMapper.selectByQuery(query).isEmpty());
    }
}