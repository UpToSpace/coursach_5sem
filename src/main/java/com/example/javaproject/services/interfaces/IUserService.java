package com.example.javaproject.services.interfaces;

import com.example.javaproject.models.Role;
import com.example.javaproject.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    User getUser(String email);
    List<User> getAllUsers();
    User saveUser(User user);
}
