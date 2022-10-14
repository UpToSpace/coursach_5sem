package com.example.javaproject.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class UserController {
    @GetMapping("/login")
    ModelAndView showLoginPage(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        //model.addAttribute("message", message);
        log.info("/login GET");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView savePerson(Model model, //
                                   @ModelAttribute("filmform") FilmForm filmForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("filmlist");
        String title = filmForm.getTitle();
        String director = filmForm.getDirector();
        if (title != null && title.length() > 0 //
                && director != null && director.length() > 0) {
            Film newFilm = new Film(title, director);
            films.add(newFilm);
            model.addAttribute("films", films);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addfilm");
        return modelAndView;
    }
}
