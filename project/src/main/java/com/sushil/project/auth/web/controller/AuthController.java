package com.sushil.project.auth.web.controller;

import com.sushil.project.auth.application.service.AuthService;
import com.sushil.project.auth.web.dto.LoginDto;
import com.sushil.project.auth.web.dto.RegisterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }


    @GetMapping("/")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        logger.info("sushil");
        return "login";
    }

    @PostMapping("/")
    public String handleLogin(@ModelAttribute("loginDto") LoginDto loginDto, Model model) {
        logger.error("Received login request for user: {}", loginDto.getUsernameOrEmail());
        logger.error("Received login request for user: {}", loginDto.getPassword());

        String ans = authService.loginDto(loginDto);
        //String ans = "Hello user";

        model.addAttribute("message", ans);
        return "home"; // name of your result template (loginResult.html)
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> handleLogin(@RequestBody LoginDto loginDto) {
//        logger.error("Received login request for user: {}", loginDto.getUsernameOrEmail());
//        logger.error("Received login request for user: {}", loginDto.getPassword());
//
//        String ans = authService.loginDto(loginDto);
//        //String ans = "Hello user";
//        return ResponseEntity.ok(ans);
//    }



    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "user is logged in");
        return "home"; // name of your home template (home.html)
    }

    @GetMapping("/register")
    public String toRegister(Model model){
//        logger.error("Received register request for user: {}", registerDto.getUsername());
//        logger.error("Received register request for user: {}", registerDto.getEmail());

        //String ans = authService.registerDto(registerDto);

        //String ans = "user registered";

        model.addAttribute("registerDto", new RegisterDto());

        return "register";
    }

    @PostMapping("/register")
    public String tohandleRegister(@ModelAttribute("registerDto") RegisterDto registerDto, Model model){
        logger.error("Received register request for user: {}", registerDto.getUsername());
        logger.error("Received register request for user: {}", registerDto.getEmail());

        String ans = authService.registerDto(registerDto);

        //String ans = "user registered";
        model.addAttribute("message", ans);

        return "home";
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> toRegister(@RequestBody RegisterDto registerDto){
//        logger.error("Received register request for user: {}", registerDto.getUsername());
//        logger.error("Received register request for user: {}", registerDto.getEmail());
//
//        String ans = authService.registerDto(registerDto);
//
//        //String ans = "user registered";
//
//        return ResponseEntity.ok(ans);
//    }
}
