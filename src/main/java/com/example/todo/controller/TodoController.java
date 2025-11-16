package com.example.todo.controller;

import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.UserEntity;
import com.example.todo.model.ApiResponse;
import com.example.todo.model.PermissionType;
import com.example.todo.model.todo.*;
import com.example.todo.service.PermissionService;
import com.example.todo.service.TodoQuery;
import com.example.todo.service.TodoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    private final PermissionService permissionService;

    private UserEntity currentUser(HttpSession session) {
        return (UserEntity) session.getAttribute(UserController.SESSION_USER_KEY);
    }

    @PostMapping
    public ApiResponse<TodoResponse> create(@Valid @RequestBody TodoCreateReq req,
                                            HttpSession session) {
        UserEntity user = currentUser(session);
        if (user == null) {
            return ApiResponse.error("user not login");
        }
        TodoEntity todo = new TodoEntity();
        BeanUtils.copyProperties(req, todo);
        todo.setUserId(user.getId());
        todo.setStatus(req.getStatus().getCode());
        if (todoService.create(todo)) {
            todo = todoService.get(todo.getId());
            return ApiResponse.success(convertToResponse(todo));
        } else {
            return ApiResponse.error("Failed to create todo");
        }
    }

    @PutMapping
    public ApiResponse<TodoResponse> update(@Valid @RequestBody TodoUpdateReq req,
                                            HttpSession session) {
        UserEntity user = currentUser(session);
        Pair<String, TodoEntity> result = checkPermission(user, req.getId(), true);
        if (result.getLeft() != null) {
            return ApiResponse.error(result.getLeft());
        }

        TodoEntity todo = new TodoEntity();
        BeanUtils.copyProperties(req, todo);
        todo.setStatus(req.getStatus().getCode());
        if (todoService.update(todo)) {
            todo = todoService.get(todo.getId());
            return ApiResponse.success(convertToResponse(todo));
        } else {
            return ApiResponse.error("Failed to create todo");
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id,
                                    HttpSession session) {
        UserEntity user = currentUser(session);
        Pair<String, TodoEntity> result = checkPermission(user, id, true);
        if (result.getLeft() != null) {
            return ApiResponse.error(result.getLeft());
        }

        if (todoService.delete(id)) {
            return ApiResponse.success();
        } else {
            return ApiResponse.error("Failed to delete todo");
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<TodoResponse> get(@PathVariable Long id,
                                         HttpSession session) {
        UserEntity user = currentUser(session);
        Pair<String, TodoEntity> result = checkPermission(user, id, true);
        if (result.getLeft() != null) {
            return ApiResponse.error(result.getLeft());
        } else {
            return ApiResponse.success(convertToResponse(result.getRight()));
        }
    }

    @GetMapping("/query")
    public ApiResponse<List<TodoResponse>> query(@Valid TodoQueryReq req,
                                                 HttpSession session) {
        UserEntity user = currentUser(session);
        TodoQuery query = new TodoQuery();
        BeanUtils.copyProperties(req, query);
        if (req.getStatus() != null) {
            query.setStatus(req.getStatus().getCode());
        }
        query.setUserId(user.getId());

        List<TodoResponse> result = todoService.query(query).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ApiResponse.success(result);
    }

    private Pair<String, TodoEntity> checkPermission(UserEntity user, Long todoId, boolean edit) {
        if (user == null) {
            return Pair.of("user not login", null);
        }
        TodoEntity old = todoService.get(todoId);
        if (old == null) {
            return Pair.of("todo not exist", null);
        }
        if (user.getId().equals(old.getUserId())) {
            return Pair.of(null, old);
        }
        PermissionType permissionType = permissionService.getPermission(user.getId(), old.getId());
        if (permissionType == null || (edit && PermissionType.EDIT != permissionType)) {
            return Pair.of("user has no permission", null);
        }
        return Pair.of(null, old);
    }

    private TodoResponse convertToResponse(TodoEntity todo) {
        TodoResponse response = new TodoResponse();
        BeanUtils.copyProperties(todo, response);
        response.setStatus(TodoStatus.fromCode(todo.getStatus()));
        return response;
    }
}