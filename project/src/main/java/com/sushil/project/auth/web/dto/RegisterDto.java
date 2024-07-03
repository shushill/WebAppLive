package com.sushil.project.auth.web.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {

    private String name;
    private String username;

    private String email;

    private String password;
}