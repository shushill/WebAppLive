package com.sushil.project.auth.application.service;

import com.sushil.project.auth.web.dto.LoginDto;
import com.sushil.project.auth.web.dto.RegisterDto;

public interface AuthService {

    String loginDto(LoginDto loginDto);
    String registerDto(RegisterDto registerDto);
}