package com.example.javaproject.repository;

import com.example.javaproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> { // 1 - какая модел репозитори, 2 - айди этого модела
    User findByEmail(String email);
    List<User> findAll();
}
