package com.sushil.project.auth.web.controller;

import com.sushil.project.auth.application.service.AuthService;
import com.sushil.project.auth.web.dto.RegisterDto;
import com.sushil.project.common.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private AuthService authService;


    public AuthController( AuthService authService) {
        this.authService = authService;

    }
    @GetMapping("/")
    public String login() {
        logger.info("sushil");
        return "login";
    }





    @GetMapping("/home")
    public String home() {
//        if(auth.isAuthenticated()){
//            return "home";
//        }else{
//            return "login";
//        }
        return "home";

    }

    @GetMapping("/register")
    public String toRegister(){

        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<ResponseDto> tohandleRegister(@Valid @RequestBody RegisterDto registerDto){
        logger.error("Received register request for user: {}", registerDto.getUsername());
        logger.error("Received register request for user: {}", registerDto.getEmail());

        boolean ans = authService.registerDto(registerDto);
        ResponseDto response;
        if(ans){
            response = new ResponseDto(ans, registerDto.getUsername());
        }else {
            response = new ResponseDto(ans, "User Registeration Failed");
        }


        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "try";
    }

    @PostMapping("/hello")
    public ResponseEntity<ResponseDto> postHello() {
        ResponseDto response = new ResponseDto(false, "Hello World");
        return new ResponseEntity<>(response, HttpStatus.OK);
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
