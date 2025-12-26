package com.substring.auth.services.impl;

import com.substring.auth.dtos.UserDto;
import com.substring.auth.services.AuthService;
import com.substring.auth.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private  final PasswordEncoder passwordEncoder;
    @Override
    public UserDto register(UserDto userDto) {
        //logic
        //verify email
        //verify password
        //default roles
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userService.createUser(userDto);
    }
}
