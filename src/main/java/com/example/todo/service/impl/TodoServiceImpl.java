package com.example.todo.service.impl;

import com.example.todo.entity.PermissionEntity;
import com.example.todo.entity.SubjectType;
import com.example.todo.entity.TodoEntity;
import com.example.todo.mapper.PermissionMapper;
import com.example.todo.mapper.TodoMapper;
import com.example.todo.model.PermissionType;
import com.example.todo.service.TodoQuery;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final Set<String> ORDER_FIELDS = Set.of("status", "due_date", "name");

    private final PermissionMapper permissionMapper;

    private final TodoMapper todoMapper;

    @Override
    @Transactional
    public boolean create(TodoEntity todo) {
        log.info("create todo");
        int count = todoMapper.insert(todo);
        PermissionEntity permission = new PermissionEntity();
        permission.setSubjectType(SubjectType.USER.getCode());
        permission.setSubjectId(todo.getUserId());
        permission.setTodoId(todo.getId());
        permission.setPermissionType(PermissionType.EDIT.getCode());
        permissionMapper.insert(permission);
        return count == 1;
    }

    @Override
    public boolean update(TodoEntity todo) {
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

