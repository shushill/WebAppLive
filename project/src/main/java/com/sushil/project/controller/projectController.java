package com.sushil.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class projectController {

    @GetMapping
    public String name(){
        return "Hello Ayushraj !!";
    }

    @GetMapping("/login")
    public String login() {
        return "login now";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
