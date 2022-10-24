package com.example.javaproject.services;

import com.example.javaproject.models.Role;
import com.example.javaproject.models.Status;
import com.example.javaproject.models.User;
import com.example.javaproject.repository.RoleRepository;
import com.example.javaproject.repository.UserRepository;
import com.example.javaproject.services.interfaces.IUserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        User user = findByEmail(email);
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByRoleId(2);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleUser);
        user.setStatus(Status.NOT_ACTIVE);

        User registeredUser = userRepository.save(user);
        log.info("user: {} successfully registered", registeredUser);
        return registeredUser;
    }

}
