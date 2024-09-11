package com.example.kinocms_user.service;

import com.example.kinocms_user.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByEmail(String email);

    void save(User user);
}