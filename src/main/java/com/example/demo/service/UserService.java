package com.example.demo.service;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.LoginAppUser;

import java.util.List;

public interface UserService {
    List<AppUser> findAll();

    AppUser findByUserName(String userName);

    LoginAppUser findByUsername(String username);
}
