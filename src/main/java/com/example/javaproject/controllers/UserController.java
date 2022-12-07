package com.example.javaproject.controllers;


import com.example.javaproject.dto.AuthResponse;
import com.example.javaproject.forms.LoginForm;
import com.example.javaproject.forms.RegistrationForm;
import com.example.javaproject.jwt.JWTProvider;
import com.example.javaproject.models.User;
import com.example.javaproject.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    //    -------------LOGIN + REGISTRATION-------------

    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, JWTProvider jwtProvider, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> allUsersPage() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @DeleteMapping("/admin/users/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "email") String email) {
        userService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        User user = userService.getUser(email, password);
        if (user == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        String accessToken = jwtProvider.generateToken(user);
        AuthResponse response = new AuthResponse(accessToken, user.getRole());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/registration")
    ResponseEntity registerUser (@RequestBody RegistrationForm registrationForm) {
        String email = registrationForm.getEmail();
        String username = registrationForm.getUsername();
        String password = registrationForm.getPassword();
        //String password = passwordEncoder.encode(registrationForm.getPassword());
        userService.registerUser(email, username, password);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @GetMapping("/user/profile")
    ResponseEntity<List<User>> getUser(@RequestHeader(name = "Authorization") String header) {
        String token = header.substring("Bearer ".length());
        String email = jwtProvider.getEmailFromToken(token);
        User user = userService.getUser(email);
        return ResponseEntity.ok().body(Collections.singletonList(user));
    }

    @PostMapping("/user/updateprofile")
    ResponseEntity updateUser(@RequestBody RegistrationForm registrationForm,
                              @RequestHeader(name = "Authorization") String header) {
        String token = header.substring("Bearer ".length());
        String email = jwtProvider.getEmailFromToken(token);
        userService.updateUser(email, registrationForm.getUsername(), registrationForm.getPassword());
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @GetMapping(value = "/isAdmin")
    public ResponseEntity<Boolean> isAdmin(@RequestHeader(name = "Authorization") String header) {
        String token = header.substring("Bearer ".length());
        String role = jwtProvider.getRoleFromToken(token);
        if(role.equals("admin")) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
        //else throw new AccountAuthException("Account not found!");
    }
}
