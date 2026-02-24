package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.validation.Validators;

import java.time.Instant;

/**
 * Project represents a Group (Foyer, Travail, Projet X...).
 * It is owned by a user.
 */
public class Project {

    private final Long id;
    private String name;
    private final Long ownerId;
    private final Instant createdAt;

    public Project(Long id, String name, Long ownerId) {
        Validators.positive(id, "Project.id");
        Validators.notBlank(name, "Project.name");
        Validators.positive(ownerId, "Project.ownerId");

        this.id = id;
        this.name = name.trim();
        this.ownerId = ownerId;
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Only the owner can rename the project.
     */
    public void rename(String newName, Long actorUserId) {
        Validators.positive(actorUserId, "actorUserId");
        if (!ownerId.equals(actorUserId)) {
            throw new BusinessRuleException("Only the owner can rename the project.");
        }
        Validators.notBlank(newName, "Project.name");
        this.name = newName.trim();
    }
}