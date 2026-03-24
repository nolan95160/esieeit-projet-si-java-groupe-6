package com.esieeit.projetsi.service;

import com.esieeit.projetsi.api.dto.TaskCreateRequest;
import com.esieeit.projetsi.api.dto.TaskUpdateRequest;
import com.esieeit.projetsi.domain.enums.TaskStatus;
import com.esieeit.projetsi.domain.exception.BusinessRuleException;
import com.esieeit.projetsi.domain.exception.InvalidDataException;
import com.esieeit.projetsi.domain.exception.ResourceNotFoundException;
import com.esieeit.projetsi.domain.model.Task;
import com.esieeit.projetsi.repository.InMemoryTaskRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final InMemoryTaskRepository taskRepository;

    public TaskService(InMemoryTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(TaskCreateRequest req) {
        Long id = taskRepository.nextId();
        Task task = new Task(id, 1L, req.getTitle(), req.getDescription(), 1L);
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
            task.rename(req.getTitle());
        }

        if (req.getDescription() != null) {
            task.changeDescription(req.getDescription());
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
            case IN_PROGRESS -> task.start();
            case DONE -> task.complete();
            case TODO -> throw new BusinessRuleException(
                    "Transition de statut interdite: " + task.getStatus() + " -> TODO");
        }
    }
}
