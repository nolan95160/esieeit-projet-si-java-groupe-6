package com.esieeit.projetsi.repository;

import com.esieeit.projetsi.domain.entity.Task;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByProjectId(Long projectId);

    List<Task> findByProjectIdAndStatus(Long projectId, TaskStatus status);

    List<Task> findByTitleContainingIgnoreCase(String keyword);

    long countByProjectId(Long projectId);

    boolean existsByProjectIdAndTitleIgnoreCase(Long projectId, String title);

    List<Task> findByProjectIdOrderByCreatedAtDesc(Long projectId);
}
