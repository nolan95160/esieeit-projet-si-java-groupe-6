package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.enums.TaskPriority;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.validation.Validators;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents a task belonging to a Project (Group).
 */
public class Task {

    private final Long id;
    private final Long projectId;

    private String title;
    private String description;

    private TaskStatus status;
    private TaskPriority priority;

    private Instant dueDate;      // nullable
    private Long assigneeId;      // nullable

    private final Long createdBy; // user id
    private final Instant createdAt;
    private Instant updatedAt;

    public Task(Long id, Long projectId, String title, String description, Long createdBy) {
        Validators.positive(id, "Task.id");
        Validators.positive(projectId, "Task.projectId");
        Validators.notBlank(title, "Task.title");
        Validators.positive(createdBy, "Task.createdBy");

        this.id = id;
        this.projectId = projectId;
        this.title = title.trim();
        this.description = description == null ? "" : description.trim();

        this.status = TaskStatus.TODO;
        this.priority = TaskPriority.MEDIUM;

        this.dueDate = null;
        this.assigneeId = null;

        this.createdBy = createdBy;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public Long getId() { return id; }
    public Long getProjectId() { return projectId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public TaskPriority getPriority() { return priority; }
    public Instant getDueDate() { return dueDate; }
    public Long getAssigneeId() { return assigneeId; }
    public Long getCreatedBy() { return createdBy; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void rename(String newTitle) {
        Validators.notBlank(newTitle, "Task.title");
        this.title = newTitle.trim();
        touch();
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription == null ? "" : newDescription.trim();
        touch();
    }

    public void setPriority(TaskPriority priority) {
        this.priority = Objects.requireNonNull(priority, "Task.priority is required.");
        touch();
    }

    public void setDueDate(Instant dueDate) {
        // nullable allowed
        this.dueDate = dueDate;
        touch();
    }

    /**
     * Assign/unassign a task. To unassign: pass null.
     * Business rule "assignee must be member of the same group" will be checked in service layer,
     * because domain layer doesn't know group membership here.
     */
    public void assignTo(Long assigneeId) {
        if (assigneeId != null) {
            Validators.positive(assigneeId, "Task.assigneeId");
        }
        this.assigneeId = assigneeId;
        touch();
    }

    /**
     * Workflow: TODO -> IN_PROGRESS
     */
    public void start() {
        if (status != TaskStatus.TODO) {
            throw new BusinessRuleException("La tâche ne peut être démarrée que si elle est en TODO.");
        }
        status = TaskStatus.IN_PROGRESS;
        touch();
    }

    /**
     * Workflow: IN_PROGRESS -> DONE
     */
    public void complete() {
        if (status != TaskStatus.IN_PROGRESS) {
            throw new BusinessRuleException("La tâche ne peut être terminée que si elle est en IN_PROGRESS.");
        }
        status = TaskStatus.DONE;
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }
}