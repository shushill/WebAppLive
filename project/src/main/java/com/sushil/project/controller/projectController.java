package com.sushil.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class projectController {

    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "login";
    }

//    @GetMapping("/register")
//    public String register() {
//        return "register";
//    }
//
//    @GetMapping("/home")
//    public String home() {
//        return "home";
//    }
}
