package com.ritesh.authservice.service;

import com.ritesh.authservice.entity.User;

public interface UserService {

    User registerUser(User user);

    String loginUser(String username, String password);
}