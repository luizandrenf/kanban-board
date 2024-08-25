package org.example.domain.application.services;

import org.example.domain.application.repositories.UserRepository;
import org.example.domain.enterprise.entities.User;

import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String username, String password){
        return userRepository.findByUsername(username);

    }

    public boolean createAccount(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // User already exists
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        userRepository.create(newUser);
        return true; // Account successful created
    }
}
