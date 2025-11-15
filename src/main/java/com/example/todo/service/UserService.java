package com.example.todo.service;

import com.example.todo.entity.UserEntity;

public interface UserService {

    UserEntity getByEmail(String email);

    UserEntity register(String email, String rawPassword);

    UserEntity login(String email, String rawPassword);
}
