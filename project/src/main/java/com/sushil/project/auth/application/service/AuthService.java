package com.sushil.project.auth.application.service;

import com.sushil.project.auth.web.dto.LoginDto;
import com.sushil.project.auth.web.dto.RegisterDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthService {

    void loginDto(LoginDto loginDto) throws UsernameNotFoundException;
    boolean registerDto(RegisterDto registerDto);
}