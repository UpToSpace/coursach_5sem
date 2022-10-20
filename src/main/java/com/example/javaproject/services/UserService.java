package com.example.javaproject.services;

import com.example.javaproject.models.User;
import com.example.javaproject.repository.UserRepository;
import com.example.javaproject.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = findByEmail(email);
        if (user.getPassword() == password) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
