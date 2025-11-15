package com.example.todo.service.impl;

import com.example.todo.entity.UserEntity;
import com.example.todo.mapper.UserMapper;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserEntity getByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Transactional
    public UserEntity register(String email, String rawPassword) {
        if (userMapper.selectByEmail(email) != null) {
            throw new IllegalArgumentException("email already exist");
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        userMapper.insert(user);
        return userMapper.selectByEmail(email);
    }

    public UserEntity login(String email, String rawPassword) {
        UserEntity user = userMapper.selectByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("user not exist");
        }
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }
}
