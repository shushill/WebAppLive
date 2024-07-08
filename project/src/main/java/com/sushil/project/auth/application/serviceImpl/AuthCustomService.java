package com.sushil.project.auth.application.serviceImpl;

import com.sushil.project.auth.domain.entity.User;
import com.sushil.project.auth.domain.repository.UserRepo;
import com.sushil.project.auth.web.controller.AuthController;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AuthCustomService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthCustomService.class);

    private final UserRepo userRepo;

    public AuthCustomService(UserRepo userRepo){
        this.userRepo = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username)  {

        //logger.info("Attempting to load user by username or email: " + usernameOrEmail);
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or Email: "));
       // User user = userRepo.findByUsername(username).get();

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        UserDetails userDetailss = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);

        return userDetailss;


    }
}