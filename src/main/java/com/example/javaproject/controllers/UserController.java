package com.example.javaproject.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.javaproject.forms.LoginForm;
import com.example.javaproject.models.Picture;
import com.example.javaproject.models.User;
import com.example.javaproject.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class UserController {
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
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> usersPage(Model model) {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

//    @GetMapping("/login")
//    public ModelAndView loginPage(HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();
//       modelAndView.setViewName("login");
////        String name = request.getParameter("name");
////        String surname = request.getParameter("surname");
////        System.out.println("name " + name + " surname " + surname);
//        return modelAndView;
//    }
//
//    @PostMapping("/login")
//    public ModelAndView loginUser () {
//        ModelAndView modelAndView = new ModelAndView();
//        //String email = loginForm.getEmail();
//        //String password = loginForm.getPassword();
//        //User user = userService.findByEmailAndPassword(email, password);
//
//        //User user = userService.findByEmailAndPassword("Valerie143@mail.ru", "1111");
//        System.out.println(userService.getAllUsers().stream().findFirst());
////        if (user != null) {
////            //authorized
////            modelAndView.setViewName("/index");@RequestBody LoginForm loginForm
////            logger.info("User logged in:\n " + user.toString());
////            return modelAndView;
////        }
////            logger.error("User " + email + " not exists");
////            //wrong email/password
////        logger.info("user not exists");
//        return modelAndView;
//    }

//    @GetMapping(value = {"/registration"})
//    ModelAndView registrationPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("registration");
//        return modelAndView;
//    }
//
//    @PostMapping(value = {"/registration"})
//    ModelAndView registerUser (RegistrationForm registrationForm) {
//        ModelAndView modelAndView = new ModelAndView();
//        String email = registrationForm.getEmail();
//        String password = registrationForm.getPassword();
//        String username = registrationForm.getUsername();
//        User user = userService.findByEmail(email);
//        if (user == null) {
//            //sending email
//            logger.info("User registered:\n " + user.toString());
//        } else {
//            //error
//        }
//        return modelAndView;
//    }
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
