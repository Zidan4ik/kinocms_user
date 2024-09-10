package com.example.kinocms_user.service.serviceImp;

import com.example.kinocms_user.repository.UserRepository;
import com.example.kinocms_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
}
