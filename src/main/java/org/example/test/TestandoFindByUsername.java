package org.example.test;

import org.example.domain.application.repositories.TaskRepository;
import org.example.domain.application.repositories.UserRepository;
import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;
import org.example.domain.enterprise.enums.TaskStatus;
import org.example.infra.DB.repositories.HibernateTaskRepository;
import org.example.infra.DB.repositories.HibernateUserRepository;

import java.util.List;
import java.util.Optional;

public class TestandoFindByUsername {
    public static void main(String[] args) {
        UserRepository userRepository =  new HibernateUserRepository();

        Optional<User> user = userRepository.findById(1L);

        TaskRepository taskRepository = new HibernateTaskRepository();

        List<Task> tasks = taskRepository.findManyByStatusAndUser(TaskStatus.TODO, user.get());

        for (Task task: tasks){
            System.out.println("Title: " + task.getTitle());
        }
    }
}
