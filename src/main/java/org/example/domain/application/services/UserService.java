package org.example.domain.application.services;

import org.example.domain.application.repositories.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


}
