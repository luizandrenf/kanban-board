package org.example.infra.DB.repositories;

import jakarta.persistence.*;
import org.example.domain.application.repositories.UserRepository;
import org.example.domain.enterprise.entities.User;

public class HibernateUserRepository implements UserRepository {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public HibernateUserRepository(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("todoListPU");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void create(User user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {

            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
        } catch (Exception e) {

            transaction.rollback();
            throw new RuntimeException(e);
        }
        finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @Override
    public User findById(Long id) {
        try {

            User user = entityManager.find(User.class, id);
            return user;

        } catch (Exception e) {

            throw new RuntimeException(e);
        } finally {

            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
