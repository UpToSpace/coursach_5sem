package com.example.javaproject.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.javaproject.dto.AuthResponse;
import com.example.javaproject.forms.LoginForm;
import com.example.javaproject.forms.RegistrationForm;
import com.example.javaproject.models.Picture;
import com.example.javaproject.models.Role;
import com.example.javaproject.models.User;
import com.example.javaproject.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final PasswordEncoder passwordEncoder;
//    ------- PICTURES ---------

//    @GetMapping(path = "/pictures/{pictureId}")
//    public Picture getPicture(@PathVariable("pictureId") Integer pictureId) {
//        return pictures.stream()
//                .filter(student -> studentId.equals(student.getStudentId()))
//                .findFirst()
//                .orElseThrow(() -> new IllegalStateException("Student "+studentId+" not found."));
//    }


//    -------------LOGIN + REGISTRATION-------------

    private final UserService userService;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> allUsersPage() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @DeleteMapping("/admin/users/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "email") String email) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody LoginForm loginForm) {
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        User user = userService.getUser(email, password);

        Algorithm algorithm = Algorithm.HMAC256("javathebest".getBytes()); //in another filter and userservice the same word
        String access_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .withClaim("role", user.getRole().getRoleName())
                .sign(algorithm);

        AuthResponse response = new AuthResponse(access_token, user.getRole());
        //response.setHeader("access_token", access_token);
        //response.setHeader("refresh_token", refresh_token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"/registration"})
    ModelAndView registerUser (@RequestBody RegistrationForm registrationForm) {
        ModelAndView modelAndView = new ModelAndView();
        String email = registrationForm.getEmail();
        String username = registrationForm.getUsername();
        String password = registrationForm.getPassword();
        //String password = passwordEncoder.encode(registrationForm.getPassword());
        userService.registerUser(email, username, password);
        return modelAndView;
    }

//@GetMapping("/token/refresh")
//public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//    String authorizationHeader = request.getHeader(AUTHORIZATION);
//    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//        try {
//            String refresh_token = authorizationHeader.substring("Bearer ".length());
//            Algorithm algorithm = Algorithm.HMAC256("javathebest".getBytes());
//            JWTVerifier verify = JWT.require(algorithm).build();
//            DecodedJWT decodedJWT = verify.verify(refresh_token);
//            String username = decodedJWT.getSubject();
//            User user = userService.getUser(username);
//            String access_token = JWT.create()
//                    .withSubject(user.getUsername())
//                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                    .withIssuer(request.getRequestURI())
//                    .withClaim("roles", user.getRole().getRoleName())
//                    .sign(algorithm);
//            Map<String, String> tokens = new HashMap<>();
//            tokens.put("access_token", access_token);
//            tokens.put("refresh_token", refresh_token);
//            response.setContentType(APPLICATION_JSON_VALUE);
//            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//        } catch (Exception exception) {
//            log.error("Error : {}", exception.getMessage());
//            response.setStatus(FORBIDDEN.value());
//            Map<String, String> errors = new HashMap<>();
//            errors.put("error_message", exception.getMessage());
//            response.setContentType(APPLICATION_JSON_VALUE);
//            new ObjectMapper().writeValue(response.getOutputStream(), errors);
//        }
//    } else {
//        throw new RuntimeException("Refresh token is missing");
//    }
//}
}
