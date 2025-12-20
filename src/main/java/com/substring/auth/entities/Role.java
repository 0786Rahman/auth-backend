package com.substring.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data

@Entity
@Table(name="roles")
public class Role {
    @Id
    private UUID id= UUID.randomUUID();
    @Column(unique = true, nullable = false)
    private String name;
}
