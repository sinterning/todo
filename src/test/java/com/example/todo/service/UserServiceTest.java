package com.example.todo.service;

import com.example.todo.entity.UserEntity;
import com.example.todo.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void register_success() {
        String email = "tom@example.com";
        UserEntity user = userService.register(email, "123456");
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(userMapper.selectByEmail(email));
    }

    @Test
    void register_fail() {
        String email = "tom@example.com";
        userService.register(email, "123456");
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.register(email, "123456"));
    }

    @Test
    void login_success() {
        String email = "tom@example.com";
        String rawPassword = "123456";
        userService.register(email, rawPassword);
        UserEntity user = userService.login(email, rawPassword);
        Assertions.assertNotNull(user);
    }

    @Test
    void login_fail() {
        String email = "tom@example.com";
        String rawPassword = "123456";
        userService.register(email, rawPassword);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.login(email, "1234"));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.login("jerry@example.com", "1234"));
    }
}
