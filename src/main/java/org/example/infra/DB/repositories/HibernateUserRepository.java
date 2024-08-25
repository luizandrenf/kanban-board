package org.example.infra.DB.repositories;

import jakarta.persistence.*;
import org.example.domain.application.repositories.UserRepository;

import org.example.domain.enterprise.entities.User;


import java.util.Optional;

public class HibernateUserRepository implements UserRepository {

    private EntityManagerFactory entityManagerFactory;


    public HibernateUserRepository(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("todoListPU");
    }

    @Override
    public void create(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
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

        }
    }

    @Override
    public Optional<User> findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {

            User user = entityManager.find(User.class, id);
            return Optional.ofNullable(user);

        } catch (Exception e) {

            throw new RuntimeException(e);
        } finally {

            entityManager.close();

        }
    }

    @Override
    public Optional<User> findByUsername(String username){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username = :username", User.class);

            query.setParameter("username", username);

            User user =  query.getSingleResult();

            return Optional.of(user);

        } catch (Exception e) {
            return Optional.empty();

        } finally {
            entityManager.close();

        }
    }

}
