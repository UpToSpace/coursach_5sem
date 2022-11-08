package com.example.javaproject.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.javaproject.forms.LoginForm;
import com.example.javaproject.forms.RegistrationForm;
import com.example.javaproject.models.User;
import com.example.javaproject.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class UserController {

    private String DBURL = "jdbc:oracle:thin:@//192.168.56.101:1521/orcl";
    private String DBUser = "sys as sysdba";
    private String DBPassword = "Vv1542139";

    Connection connection = DriverManager.getConnection(DBURL, DBUser, DBPassword);
    Logger logger = LoggerFactory.getLogger(UserController.class);

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
    public UserController(UserService userService) throws SQLException {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> usersPage(Model model) {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/login")
    public ModelAndView loginPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
       modelAndView.setViewName("login");
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        System.out.println("name " + name + " surname " + surname);
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginUser (@RequestBody LoginForm loginForm) {
        ModelAndView modelAndView = new ModelAndView();
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();

        try {
            CallableStatement cs = connection.prepareCall("{call log_in_user(?, ?)}");
            cs.setString(1, email);
            cs.setString(2, password);
            cs.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        modelAndView.setViewName("/index");
        return modelAndView;
    }

    @GetMapping(value = {"/registration"})
    ModelAndView registrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = {"/registration"})
    ModelAndView registerUser (@RequestBody RegistrationForm registrationForm) {
        ModelAndView modelAndView = new ModelAndView();
        String email = registrationForm.getEmail();
        String password = registrationForm.getPassword();
        String username = registrationForm.getUsername();

        try {
        CallableStatement cs = connection.prepareCall("{call register_user(?, ?, ?)}");
        cs.setString(1, email);
        cs.setString(2, username);
        cs.setString(3, password);
        cs.executeQuery();
    } catch (SQLException e) {
            System.out.println(e.getMessage());;
    }
        return modelAndView;
    }

@GetMapping("/token/refresh")
public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String authorizationHeader = request.getHeader(AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        try {
            String refresh_token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("javathebest".getBytes());
            JWTVerifier verify = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verify.verify(refresh_token);
            String username = decodedJWT.getSubject();
            User user = userService.getUser(username);
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURI())
                    .withClaim("roles", user.getRole().getRoleName())
                    .sign(algorithm);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            tokens.put("refresh_token", refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), tokens);
        } catch (Exception exception) {
            log.error("Error : {}", exception.getMessage());
            response.setStatus(FORBIDDEN.value());
            Map<String, String> errors = new HashMap<>();
            errors.put("error_message", exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), errors);
        }
    } else {
        throw new RuntimeException("Refresh token is missing");
    }
}
}
