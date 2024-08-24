package org.example.test;


import org.example.domain.application.repositories.TaskRepository;
import org.example.domain.application.repositories.UserRepository;

import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;

import org.example.infra.DB.repositories.HibernateTaskRepository;
import org.example.infra.DB.repositories.HibernateUserRepository;

import java.util.List;


public class PersistenceTest {

    public static void main(String[] args) {


        UserRepository userRepository = new HibernateUserRepository();

        TaskRepository taskRepository = new HibernateTaskRepository();

        User existentUser = userRepository.findById(1L);

        List<Task> taskList = taskRepository.findManyByUser(existentUser);

        for (Task task : taskList) {
            System.out.println("Title: " + task.getTitle());
        }

    }
}
