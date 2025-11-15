package com.example.todo.mapper;

import com.example.todo.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper {
    int insert(RoleEntity role);

    int updateById(RoleEntity role);

    int deleteById(@Param("id") Long id);

    RoleEntity selectById(@Param("id") Long id);

    RoleEntity selectByName(@Param("name") String name);
}