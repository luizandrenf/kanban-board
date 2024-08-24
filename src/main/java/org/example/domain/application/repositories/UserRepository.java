package org.example.domain.application.repositories;

import org.example.domain.enterprise.entities.User;

public interface UserRepository {
    void create(User user);
    User findById(Long id);
}
