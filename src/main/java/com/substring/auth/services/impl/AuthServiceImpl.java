package com.substring.auth.services.impl;

import com.substring.auth.dtos.UserDto;
import com.substring.auth.services.AuthService;
import com.substring.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    @Override
    public UserDto register(UserDto userDto) {
        return userService.createUser(userDto);
    }
}
