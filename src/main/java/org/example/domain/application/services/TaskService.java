package org.example.domain.application.services;

import org.example.domain.application.repositories.TaskRepository;
import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;
import org.example.domain.enterprise.enums.TaskStatus;

import java.util.List;
import java.util.Optional;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public void create(Task task){
        this.taskRepository.create(task);
    }

    public void delete(Long id){
        this.taskRepository.deleteById(id);
    }

    public void update(Task task){
        this.taskRepository.update(task);
    }


    public List<Task> findManyByStatusAndUser(String status, User user){
        return this.taskRepository.findManyByStatusAndUser(TaskStatus.valueOf(status), user);
    }

    public Optional<Task> findById(Long id){
        return this.taskRepository.findById(id);
    }
}
