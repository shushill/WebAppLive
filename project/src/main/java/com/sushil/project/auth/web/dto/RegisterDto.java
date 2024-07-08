package com.sushil.project.auth.web.dto;

import lombok.*;

import javax.validation.constraints.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, message = "Name must have at least 2 characters")
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, message = "Username must have at least 3 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
}