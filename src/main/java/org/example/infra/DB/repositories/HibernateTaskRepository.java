package org.example.infra.DB.repositories;

import jakarta.persistence.*;
import org.example.domain.application.repositories.TaskRepository;
import org.example.domain.enterprise.entities.Task;
import org.example.domain.enterprise.entities.User;

import java.util.List;

public class HibernateTaskRepository implements TaskRepository {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public HibernateTaskRepository(){
        this.entityManagerFactory = Persistence.createEntityManagerFactory("todoListPU");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void create(Task task) {
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
            entityManagerFactory.close();
        }
    }

    @Override
    public void deleteById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Task task = entityManager.find(Task.class, id);
            entityManager.remove(task);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @Override
    public Task findById(Long id) {
        try {
            Task task = entityManager.find(Task.class, id);
            return task;

        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

    @Override
    public List<Task> findManyByUser(User user) {
        try {
            Query query = entityManager.createQuery("select t from Task t where t.user = :userParam");

            query.setParameter("userParam", user);

            List<Task> taskList =  query.getResultList();

            return  taskList;

        } catch (Exception e){
            throw new RuntimeException(e);

        } finally {

            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
