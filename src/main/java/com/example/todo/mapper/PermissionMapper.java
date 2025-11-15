package com.example.todo.mapper;

import com.example.todo.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    int insert(PermissionEntity permission);

    int delete(@Param("subjectType") Integer subjectType,
               @Param("subjectId") Long subjectId,
               @Param("todoId") Long todoId);

    int deleteByRole(Long roleId);

    int deleteByTodo(Long todoId);

    PermissionEntity selectPermission(@Param("subjectType") Integer subjectType,
                                      @Param("subjectId") Long subjectId,
                                      @Param("todoId") Long todoId);


    List<PermissionEntity> selectUserPermission(@Param("userId") Long userId,
                                                @Param("todoId") Long todoId);
}

