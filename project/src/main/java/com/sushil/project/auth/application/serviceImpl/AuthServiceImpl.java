package com.sushil.project.auth.application.serviceImpl;

import com.sushil.project.auth.application.service.AuthService;
import com.sushil.project.auth.domain.entity.Roles;
import com.sushil.project.auth.domain.entity.User;
import com.sushil.project.auth.domain.repository.RolesRepo;
import com.sushil.project.auth.domain.repository.UserRepo;
import com.sushil.project.auth.web.dto.LoginDto;
import com.sushil.project.auth.web.dto.RegisterDto;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepo userRepo;
    private RolesRepo rolesRepo;

    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepo userRepo,
                           RolesRepo rolesRepo,
                           PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.rolesRepo = rolesRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String loginDto(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User logged in Successfully";
    }

    @Override
    public String registerDto(RegisterDto registerDto){

//        Optional<User> userByUsername = userRepo.findByUsername(registerDto.getUsername());

//        if (userByUsername.isPresent()) {
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
//        }
//
//        Optional<User> userByEmail = userRepo.findByEmail(registerDto.getEmail());
//
//        if (userByEmail.isPresent()) {
//            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
//        }



        User user = new User();

        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));



        Set<Roles> roles = new HashSet<>();
        Optional<Roles> userRole = rolesRepo.findByName("ROLE_USER");
        String ans ="";
        if(userRole.isPresent()){
            roles.add(userRole.get());
        }
        else{
            Roles roles1 = new Roles();
            roles1.setName("ROLE_USER");
            rolesRepo.save(roles1);
            roles.add(roles1);
        }
        user.setRoles(roles);
        userRepo.save(user);


       // return ans;
        return "User registered successfully";

    }
}