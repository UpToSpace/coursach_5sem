package com.example.javaproject.services.interfaces;

import com.example.javaproject.models.Role;
import com.example.javaproject.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    User getUser(String email, String password);
    List<User> getAllUsers();
    void registerUser(String email, String username, String password);
    void deleteUser(String email);
}
