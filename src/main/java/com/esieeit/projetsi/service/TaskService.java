package com.esieeit.projetsi.service;

import com.esieeit.projetsi.api.dto.TaskCreateRequest;
import com.esieeit.projetsi.api.dto.TaskUpdateRequest;
import com.esieeit.projetsi.domain.entity.Project;
import com.esieeit.projetsi.domain.entity.Task;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.exception.InvalidDataException;
import com.esieeit.projetsi.domain.exception.ResourceNotFoundException;
import com.esieeit.projetsi.repository.ProjectRepository;
import com.esieeit.projetsi.repository.TaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public Task create(TaskCreateRequest req) {
        Project project = projectRepository.findById(req.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project introuvable: id=" + req.getProjectId()));

        if (taskRepository.existsByProjectIdAndTitleIgnoreCase(project.getId(), req.getTitle())) {
            throw new BusinessRuleException("Une tâche avec le même titre existe déjà dans ce projet");
        }

        Task task = new Task();
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setProject(project);

        return taskRepository.save(task);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task introuvable: id=" + id));
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task update(Long id, TaskUpdateRequest req) {
        Task task = findById(id);

        if (req.getTitle() != null) {
            task.setTitle(req.getTitle());
        }

        if (req.getDescription() != null) {
            task.setDescription(req.getDescription());
        }

        if (req.getStatus() != null) {
            TaskStatus newStatus;
            try {
                newStatus = TaskStatus.valueOf(req.getStatus());
            } catch (IllegalArgumentException e) {
                throw new InvalidDataException("Statut invalide: " + req.getStatus());
            }
            applyStatusTransition(task, newStatus);
        }

        return taskRepository.save(task);
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task introuvable: id=" + id);
        }
        taskRepository.deleteById(id);
    }

    private void applyStatusTransition(Task task, TaskStatus newStatus) {
        if (task.getStatus() == newStatus) {
            return;
        }
        switch (newStatus) {
            case IN_PROGRESS -> task.markInProgress();
            case DONE -> task.markDone();
            case TODO -> throw new BusinessRuleException(
                    "Transition de statut interdite: " + task.getStatus() + " -> TODO");
            default -> task.setStatus(newStatus);
        }
    }
}
