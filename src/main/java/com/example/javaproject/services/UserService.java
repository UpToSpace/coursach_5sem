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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public User loginUser(String email) {
//        return userRepository.loginUser(email);
//    }
//
//    @Override
//    public User findByEmailAndPassword(String email, String password) {
//        User user = loginUser(email);
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

//    @Override
//    public User saveUser(User user) { //registaration
//        //user.setPassword(passwordEncoder.encode(user.getPassword()));
//        System.out.println("saveuser pass: " + user.getPassword());
//        return userRepository.save(user);
//    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.loginUser(email);
//        if (user == null) {
//            log.error("userService: user doesnt exist");
//            throw new UsernameNotFoundException("e");
//        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
//    }
}
