package com.sushil.project.auth.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String username;
    private String password;
}