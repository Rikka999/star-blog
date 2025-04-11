package com.mc.starblog;

import com.mc.starblog.Repository.RoleRepository;
import com.mc.starblog.Repository.UserRepository;
import com.mc.starblog.entity.Role;
import com.mc.starblog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class StarBlogApplicationTests {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {

    }

}
