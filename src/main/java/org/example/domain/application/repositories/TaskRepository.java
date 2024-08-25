package org.example.domain.application.repositories;

import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;
import org.example.domain.enterprise.enums.Status;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void create(Task task);
    void deleteById(Long id);
    void update(Task task);
    Optional<Task> findById(Long id);
    Optional<Task> findByTitle(String title);
    List<Task> findManyByUser(User user);
    List<Task> findManyByStatusAndUser(Status status, User user);
}
