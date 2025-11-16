package com.example.todo.controller;

import com.example.todo.BaseTest;
import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.model.ApiResponse;
import com.example.todo.model.todo.*;
import com.example.todo.service.TodoService;
import com.example.todo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;

import java.time.LocalDate;

class TodoControllerTest extends BaseTest {

    @Autowired
    private UserService userService;
    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoController todoController;

    private final HttpSession session = new MockHttpSession();

    @BeforeEach()
    void login() {
        UserEntity user = userService.register("tom@example.com", "123456");
        session.setAttribute(UserController.SESSION_USER_KEY, user);
    }

    @Test
    void create() {
        TodoCreateReq req = new TodoCreateReq();
        req.setName("to_do_1");
        req.setStatus(TodoStatus.NOT_STARTED);
        req.setDueDate(LocalDate.now().plusDays(1));

        Assertions.assertTrue(todoController.create(req, session).isSuccess());
    }

    @Test
    void update() {
        TodoEntity todo = createTodo();

        TodoUpdateReq req = new TodoUpdateReq();
        req.setId(todo.getId());
        req.setName(todo.getName());
        req.setStatus(TodoStatus.fromCode(todo.getStatus()));
        req.setDueDate(todo.getDueDate().plusDays(1));
        req.setDescription(todo.getDescription());

        Assertions.assertTrue(todoController.update(req, session).isSuccess());
        req.setId(-1L);
        Assertions.assertFalse(todoController.update(req, session).isSuccess());
    }

    @Test
    void delete() {
        TodoEntity todo = createTodo();

        Assertions.assertTrue(todoController.delete(todo.getId(), session).isSuccess());
        Assertions.assertFalse(todoController.delete(todo.getId(), session).isSuccess());
    }

    @Test
    void get() {
        TodoEntity todo = createTodo();

        ApiResponse<TodoResponse> resp = todoController.get(todo.getId(), session);
        Assertions.assertTrue(resp.isSuccess());
        Assertions.assertNotNull(resp.getData());

        resp = todoController.get(-1L, session);
        Assertions.assertFalse(resp.isSuccess());
        Assertions.assertNull(resp.getData());
    }

    @Test
    void query() {
        TodoEntity todo = createTodo();

        TodoQueryReq req = new TodoQueryReq();
        req.setName(todo.getName());
        Assertions.assertEquals(1, todoController.query(req, session).getData().size());
        req.setName("test");
        Assertions.assertTrue(todoController.query(req, session).getData().isEmpty());

        req.setName(null);
        req.setStatus(TodoStatus.fromCode(todo.getStatus()));
        Assertions.assertEquals(1, todoController.query(req, session).getData().size());
        req.setStatus(TodoStatus.IN_PROGRESS);
        Assertions.assertTrue(todoController.query(req, session).getData().isEmpty());

        req.setStatus(null);
        req.setDueDateFrom(todo.getDueDate());
        Assertions.assertEquals(1, todoController.query(req, session).getData().size());
        req.setDueDateFrom(todo.getDueDate().plusDays(1));
        Assertions.assertTrue(todoController.query(req, session).getData().isEmpty());

        req.setDueDateFrom(null);
        req.setDueDateTo(todo.getDueDate());
        Assertions.assertEquals(1, todoController.query(req, session).getData().size());
        req.setDueDateTo(todo.getDueDate().plusDays(-1));
        Assertions.assertTrue(todoController.query(req, session).getData().isEmpty());
    }

    private TodoEntity createTodo() {
        UserEntity user = (UserEntity) session.getAttribute(UserController.SESSION_USER_KEY);
        TodoEntity todo = createTodo(user);
        todoService.create(todo);
        return todo;
    }

}
