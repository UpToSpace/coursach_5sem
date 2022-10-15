package com.example.javaproject.controllers;


import com.example.javaproject.forms.LoginForm;
import com.example.javaproject.models.User;
import com.example.javaproject.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class UserController {



    @Autowired
    private UserService userService;

    @PostMapping(value = {"/login"})
    ModelAndView loginUser (LoginForm loginForm) {
        ModelAndView modelAndView = new ModelAndView();
        String email = loginForm.getEmail();
        String password = loginForm.getPassword();
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            modelAndView.setViewName("index");
        } else {
        }
        return modelAndView;
    }
}
