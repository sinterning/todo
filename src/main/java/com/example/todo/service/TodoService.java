package com.example.todo.service;

import com.example.todo.entity.TodoEntity;

import java.util.List;

public interface TodoService {
    boolean create(TodoEntity todo);

    boolean update(TodoEntity todo);

    boolean delete(Long id);

    TodoEntity get(Long id);

    List<TodoEntity> query(TodoQuery query);
}
