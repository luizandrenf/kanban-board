package org.example.domain.application.repositories;

import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;
import org.example.domain.enterprise.enums.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void create(Task task);
    void deleteById(Long id);
    void update(Task task);
    Optional<Task> findById(Long id);
    List<Task> findManyByStatusAndUser(TaskStatus status, User user);
}
