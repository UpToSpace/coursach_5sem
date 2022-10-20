package com.example.javaproject.services.interfaces;

import com.example.javaproject.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    List<User> findAll();
}
