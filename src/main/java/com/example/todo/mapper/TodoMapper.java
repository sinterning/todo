package com.example.todo.mapper;

import com.example.todo.entity.TodoEntity;
import com.example.todo.service.TodoQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TodoMapper {
    int insert(TodoEntity item);

    int updateById(TodoEntity item);

    int deleteById(@Param("id") Long id);

    TodoEntity selectById(@Param("id") Long id);

    List<TodoEntity> selectByQuery(@Param("query") TodoQuery query);

}