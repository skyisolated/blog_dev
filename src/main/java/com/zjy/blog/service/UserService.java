package com.zjy.blog.service;

import com.zjy.blog.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User checkUser(String username, String password);
}
