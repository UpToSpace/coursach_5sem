package com.example.javaproject.services;

import com.example.javaproject.models.User;
import com.example.javaproject.repository.UserRepository;
import com.example.javaproject.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String email, String password) {
        return userRepository.loginUser(email, password);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void registerUser(String email, String username, String password) {
        userRepository.registerUser(email, username, password );
    }

    @Override
    public User getUser(String email) {
        return userRepository.getUser(email);
    }

    @Override
    public void updateUser(String email, String username, String password) {
        userRepository.updateUser(email, username, password);
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteUser(email);
    }

}
