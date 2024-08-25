package org.example.domain.application.repositories;

import org.example.domain.enterprise.entities.User;

import java.util.Optional;

public interface UserRepository {
    void create(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
}
