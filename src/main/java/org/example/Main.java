package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.domain.enterprise.entities.User;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("todoListPU");

        EntityManager entityManager = emf.createEntityManager();

        entityManager.getTransaction().begin();

        User user = new User("Luiz", "luizandrenf", "123456");

        entityManager.persist(user);

        entityManager.getTransaction().commit();

        entityManager.close();
        emf.close();


    }
}