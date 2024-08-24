package org.example.domain.application.repositories;

import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;

import java.util.List;

public interface TaskRepository {
    void create(Task task);
    void deleteById(Long id);
    Task findById(Long id);
    List<Task> findManyByUser(User user);
}
