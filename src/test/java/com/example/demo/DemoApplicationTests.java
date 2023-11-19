package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserService userService;
    @Test
    void contextLoads() {
        User user=new User();
        user.setAccount("admin");
        user.setPassword("1231456");
        userService.login("admin","1231456");
    }

}
