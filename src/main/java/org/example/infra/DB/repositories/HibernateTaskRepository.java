package org.example.infra.DB.repositories;

import jakarta.persistence.*;
import org.example.domain.application.repositories.TaskRepository;
import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;
import org.example.domain.enterprise.enums.TaskStatus;

import java.util.List;
import java.util.Optional;

public class HibernateTaskRepository implements TaskRepository {

    private EntityManagerFactory entityManagerFactory;

    public HibernateTaskRepository(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("todoListPU");
    }

    @Override
    public void create(Task task) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(task);
            transaction.commit();

        } catch (Exception e){
            transaction.rollback();
            throw new RuntimeException(e);

        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Task task){
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(task);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);

        } finally {
            entityManager.close();

        }
    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Task task = entityManager.find(Task.class, id);

            if (task != null) {
                entityManager.remove(task);
            }
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);

        } finally {
            entityManager.close();

        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            Task task = entityManager.find(Task.class, id);
            return Optional.ofNullable(task);

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            entityManager.close();

        }
    }

    @Override
    public List<Task> findManyByStatusAndUser(TaskStatus status, User user) {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Task> query = entityManager.createQuery("select t from Task t where t.user = :userParam and t.status = :paramStatus", Task.class);

            query.setParameter("userParam", user);
            query.setParameter("paramStatus", status);

            List<Task> taskList =  query.getResultList();

            return taskList;

        } catch (Exception e){
            throw new RuntimeException(e);

        } finally {

            entityManager.close();
        }
    }
}
