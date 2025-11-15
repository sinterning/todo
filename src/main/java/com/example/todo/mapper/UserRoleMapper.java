package com.example.todo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
    int insert(@Param("roleId") Long roleId, @Param("userId") Long userId);

    int select(@Param("roleId") Long roleId, @Param("userId") Long userId);

    int delete(@Param("roleId") Long roleId, @Param("userId") Long userId);

    int deleteByRole(Long roleId);
}