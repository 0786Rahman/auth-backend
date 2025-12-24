package com.substring.auth.services.impl;

import com.substring.auth.dtos.UserDto;
import com.substring.auth.entities.Provider;
import com.substring.auth.entities.User;
import com.substring.auth.exception.ResourceNotFoundException;
import com.substring.auth.reposiries.UserRepo;
import com.substring.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        if(userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = modelMapper.map(userDto, User.class);
        user.setProvider(userDto.getProvider()!=null?userDto.getProvider(): Provider.LOCAL);
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepo.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        modelMapper.map(userDto, user);
        User updatedUser = userRepo.save(user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepo.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepo.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Iterable<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(user -> modelMapper.map(user,UserDto.class)).toList();
    }
}
