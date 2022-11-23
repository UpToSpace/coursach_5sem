package com.example.javaproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BasicController {

    @GetMapping("/login")
    public String loginPage() {
        return "/login";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "/registration";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "/admin";
    }

    @GetMapping("/pictures")
    public String picturesPage() {
        return "/pictures";
    }

    @GetMapping("/authors")
    public String authorsPage() {
        return "/authors";
    }

    @GetMapping("/categories")
    public String categoriesPage() {
        return "/categories";
    }

    @GetMapping("/profile")
    public String profilePage() {
        return "/profile";
    }

    @GetMapping("/collections")
    public String collectionsPage() {
        return "/collections";
    }

}
