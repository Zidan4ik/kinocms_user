package com.example.kinocms_user.auth;

import com.example.kinocms_user.entity.User;
import com.example.kinocms_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByLoaded = userService.getByEmail(username);
        return userByLoaded.map(UserDetailsImp::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found!"));
    }
}
