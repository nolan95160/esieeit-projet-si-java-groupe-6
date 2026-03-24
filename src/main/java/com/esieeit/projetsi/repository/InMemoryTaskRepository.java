package com.esieeit.projetsi.repository;

import com.esieeit.projetsi.domain.model.Task;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTaskRepository {

    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public Task save(Task task) {
        store.put(task.getId(), task);
        return task;
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    public Long nextId() {
        return sequence.getAndIncrement();
    }
}
