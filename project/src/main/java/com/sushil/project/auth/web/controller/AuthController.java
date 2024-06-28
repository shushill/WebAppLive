package com.sushil.project.auth.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {



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
