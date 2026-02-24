package com.esieeit.projetsi.domain.model;

import com.esieeit.projetsi.domain.validation.Validators;

import java.time.Instant;

/**
 * Optional comment attached to a Task.
 */
public class Comment {

    private final Long id;
    private final Long taskId;
    private final Long authorId;
    private final String content;
    private final Instant createdAt;

    public Comment(Long id, Long taskId, Long authorId, String content) {
        Validators.positive(id, "Comment.id");
        Validators.positive(taskId, "Comment.taskId");
        Validators.positive(authorId, "Comment.authorId");
        Validators.notBlank(content, "Comment.content");
        Validators.maxLength(content, 300, "Comment.content");

        this.id = id;
        this.taskId = taskId;
        this.authorId = authorId;
        this.content = content.trim();
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public Long getTaskId() { return taskId; }
    public Long getAuthorId() { return authorId; }
    public String getContent() { return content; }
    public Instant getCreatedAt() { return createdAt; }
}