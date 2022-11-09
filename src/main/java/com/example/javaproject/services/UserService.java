package com.example.javaproject.services;

import com.example.javaproject.models.User;
import com.example.javaproject.repository.RoleRepository;
import com.example.javaproject.repository.UserRepository;
import com.example.javaproject.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public User findByEmailAndPassword(String email, String password) {
//        User user = findByEmail(email);
//        if (user.getPassword().equals(password)) {
//            return user;
//        }
//        return null;
//    }
//    @Override
//    public User register(User user) {
//        Role roleUser = roleRepository.findByRoleId(2);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRole(roleUser);
//        user.setStatus(Status.NOT_ACTIVE);
//
//        User registeredUser = userRepository.save(user);
//        log.info("user: {} successfully registered", registeredUser);
//        return registeredUser;
//    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) { //registaration
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("saveuser pass: " + user.getPassword());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("userService: user doesnt exist");
            throw new UsernameNotFoundException("e");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
