package com.example.todo.service.impl;

import com.example.todo.entity.TodoEntity;
import com.example.todo.mapper.PermissionMapper;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.service.TodoQuery;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Service
public class TodoServiceImpl implements TodoService {
    private final Set<String> ORDER_FIELDS = Set.of("status", "due_date", "name");

    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private TodoMapper todoMapper;

    @Override
    @Transactional()
    public boolean create(TodoEntity todo) {
        return todoMapper.insert(todo) == 1;
    }

    @Override
    @Transactional
    public boolean update(TodoEntity todo) {
        if (todo.getId() == null) {
            throw new IllegalArgumentException("ID 不能为空");
        }
        return todoMapper.updateById(todo) == 1;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        permissionMapper.deleteByTodo(id);
        return todoMapper.deleteById(id) == 1;
    }

    @Override
    public TodoEntity get(Long id) {
        return todoMapper.selectById(id);
    }

    @Override
    public List<TodoEntity> query(TodoQuery query) {
        if (StringUtils.hasText(query.getOrderBy())) {
            String[] arr = query.getOrderBy().split(",");
            for (String s : arr) {
                String col = s.trim().replaceAll("\\s+(?i)(asc|desc)$", "");
                if (!ORDER_FIELDS.contains(col)) {
                    throw new IllegalArgumentException("非法排序字段: " + col);
                }
            }
        }
        return todoMapper.selectByQuery(query);
    }
}

