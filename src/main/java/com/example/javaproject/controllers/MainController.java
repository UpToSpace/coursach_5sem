package com.example.javaproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @GetMapping("/pictures") // url: /pictures/picture?id=0
    public String picturePage(@RequestParam("picture") int id) { //
        return "";
    }
}
