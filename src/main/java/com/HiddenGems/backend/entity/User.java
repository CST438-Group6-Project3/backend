package com.HiddenGems.backend.entity;

import jakarta.persistence.*;
<<<<<<< Updated upstream
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

// TEMPORARY USER ENTITY (Ruben should have one made)
=======
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

>>>>>>> Stashed changes
@Entity
@Table(name = "users")
public class User {

<<<<<<< Updated upstream
    public enum Role {
        user,
        admin
    }

=======
>>>>>>> Stashed changes
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

<<<<<<< Updated upstream
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.user;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructors
    public User() {}

    // Getters & Setters

=======
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
>>>>>>> Stashed changes
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

<<<<<<< Updated upstream
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
=======
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
>>>>>>> Stashed changes
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

<<<<<<< Updated upstream
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
=======
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
>>>>>>> Stashed changes
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
<<<<<<< Updated upstream
}
=======

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
>>>>>>> Stashed changes
