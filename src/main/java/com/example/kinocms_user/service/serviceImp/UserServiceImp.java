package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.entity.User;
import com.example.kinocms_user.repository.UserRepository;
import com.example.kinocms_user.service.UserService;
import com.example.kinocms_user.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getByEmail(String email) {
        LogUtil.logGetNotification("user", "email", email);
        Optional<User> userByEmail = userRepository.findByEmail(email);
        LogUtil.logGetInfo("User", "email", email, userByEmail.isPresent());
        return userByEmail;
    }

    @Override
    public void save(User user) {
        LogUtil.logSaveNotification("user", "id", user.getId());
        userRepository.save(user);
        LogUtil.logSaveInfo("User", "id", user.getId());
    }

    @Override
    public Optional<User> getById(Long id) {
        LogUtil.logGetNotification("user", "id", id);
        Optional<User> userById = userRepository.findById(id);
        LogUtil.logGetInfo("User", "id", id, userById.isPresent());
        return userById;
    }
}
