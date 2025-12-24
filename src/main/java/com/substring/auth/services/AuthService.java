package com.substring.auth.services;

import com.substring.auth.dtos.UserDto;

public interface AuthService {
    UserDto register(UserDto userDto);
}
