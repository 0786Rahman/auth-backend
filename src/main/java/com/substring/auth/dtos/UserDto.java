package com.substring.auth.dtos;

import com.substring.auth.entities.Provider;
import com.substring.auth.entities.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Data
public class UserDto {
    private UUID id;
    private String userName;
    private String email;
    private String password;
    private String image;
    private boolean enabled;
    Instant createdAt;
    Instant updatedAt;
    private Provider provider;
    private Set<RoleDto> roles=new HashSet<>();
}
