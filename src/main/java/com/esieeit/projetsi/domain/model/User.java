package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.enums.UserRole;
import com.esieeit.projetsi.domain.validation.Validators;

/**
 * Represents a user of the application.
 */
public class User {

    private final Long id;
    private final String email;
    private String displayName;
    private String passwordHash;
    private UserRole role;
    private boolean active;

    public User(Long id, String email, String passwordHash, String displayName) {
        Validators.positive(id, "User.id");
        Validators.email(email, "User.email");
        Validators.notBlank(passwordHash, "User.passwordHash");
        Validators.notBlank(displayName, "User.displayName");

        this.id = id;
        this.email = email.trim().toLowerCase();
        this.passwordHash = passwordHash;
        this.displayName = displayName.trim();
        this.role = UserRole.USER;
        this.active = true;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public void changeDisplayName(String newName) {
        Validators.notBlank(newName, "User.displayName");
        this.displayName = newName.trim();
    }

    public void changePassword(String newPasswordHash) {
        Validators.notBlank(newPasswordHash, "User.passwordHash");
        this.passwordHash = newPasswordHash;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }
}