package org.example.domain.application.services;

import org.example.domain.application.repositories.TaskRepository;
import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;
import org.example.domain.enterprise.enums.Status;

import java.util.List;
import java.util.Optional;

public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
       this.taskRepository = taskRepository;
    }

    public void addTask(Task task){
        this.taskRepository.create(task);
    }

    public Optional<Task> findTaskByTitle(String title){
        return this.taskRepository.findByTitle(title);
    }

    public List<Task> findManyTasksByUser(User user){
        return this.taskRepository.findManyByUser(user);
    }

    public List<Task> findManyByStatusAndUser(String status, User user){
        return this.taskRepository.findManyByStatusAndUser(Status.valueOf(status), user);
    }

    public Optional<Task> findById(Long id){
        return this.taskRepository.findById(id);
    }

    public void deleteTask(Long id){
        this.taskRepository.deleteById(id);
    }

    public void update(Task task){
        this.taskRepository.update(task);
    }
}
