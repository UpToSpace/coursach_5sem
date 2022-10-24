package com.example.javaproject.repository;

import com.example.javaproject.models.Role;
import com.example.javaproject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleId(Integer id);
    List<Role> findAll();
}
