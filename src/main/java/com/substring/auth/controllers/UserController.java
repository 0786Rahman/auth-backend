package com.substring.auth.controllers;

import com.substring.auth.dtos.UserDto;
import com.substring.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Iterable<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
    // DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    // GET BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // GET BY ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
    // UPDATE
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @RequestBody UserDto userDto,
            @PathVariable String userId) {
        return ResponseEntity.ok(userService.updateUser(userDto, userId));
    }



}
