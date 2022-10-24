package com.example.javaproject.controllers;


import com.example.javaproject.forms.LoginForm;
import com.example.javaproject.models.Picture;
import com.example.javaproject.models.User;
import com.example.javaproject.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

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
    @Lazy
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> usersPage(Model model) {
        return userService.findAllUsers();
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
        User user = userService.findByEmailAndPassword(email, password);

        //User user = userService.findByEmailAndPassword("Valerie143@mail.ru", "1111");
        //System.out.println(userService.findAll().stream().findFirst());
        if (user != null) {
            //authorized
            modelAndView.setViewName("/index");
            logger.info("User logged in:\n " + user.toString());
            return modelAndView;
        }
            logger.error("User " + email + " not exists");
            //wrong email/password
        logger.info("user not exists");
        return modelAndView;
    }

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
}
