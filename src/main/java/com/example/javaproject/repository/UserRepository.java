package com.example.javaproject.repository;

import com.example.javaproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> { // 1 - какая модел репозитори, 2 - айди этого модела
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
}
