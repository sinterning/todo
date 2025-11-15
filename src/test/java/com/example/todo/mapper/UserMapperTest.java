package com.example.todo.mapper;

import com.example.todo.BaseTest;
import com.example.todo.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
@SpringBootTest
public class UserMapperTest extends BaseTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void crud() {
        // insert
        UserEntity u = createUser();
        Assertions.assertEquals(1, userMapper.insert(u));
        Assertions.assertNotNull(u.getId());

        // select
        UserEntity load = userMapper.selectByEmail(u.getEmail());
        Assertions.assertNotNull(load);
        Assertions.assertEquals(u.getEmail(), load.getEmail());
    }
}
