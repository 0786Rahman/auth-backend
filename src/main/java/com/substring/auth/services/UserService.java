package com.substring.auth.services;

import com.substring.auth.dtos.UserDto;

public interface UserService {
UserDto createUser(UserDto userDto);
UserDto updateUser(UserDto userDto,String userId);
UserDto getUserByEmail(String email);
void deleteUser(String userId);
UserDto getUserById(String userId);
Iterable<UserDto> getAllUsers();
}
