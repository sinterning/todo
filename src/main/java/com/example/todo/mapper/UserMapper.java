package com.example.todo.mapper;

import com.example.todo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    int insert(UserEntity user);

    UserEntity selectByEmail(@Param("email") String email);
}