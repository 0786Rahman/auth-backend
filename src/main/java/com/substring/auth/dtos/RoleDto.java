package com.substring.auth.dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.UUID;
@Data
public class RoleDto {
    private UUID id;
    private String name;
}
