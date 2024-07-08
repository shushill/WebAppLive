package com.sushil.project.auth.application.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushil.project.auth.domain.entity.User;
import com.sushil.project.auth.domain.repository.UserRepo;
import com.sushil.project.auth.exception.LoginFailedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Map;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/login_req", "POST"));

        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);

        byte[] requestBodyBytes = cachedRequest.getCachedBody();

        if (requestBodyBytes.length == 0) {
            throw new BadCredentialsException("Request body is empty");
        }

        Map<String, String> requestBody = objectMapper.readValue(requestBodyBytes, Map.class);

        String username = requestBody.get("username");
        String password = requestBody.get("password");


        logger.info("Attempting authentication for user: {}", username);
        logger.info("Attempting authentication for user: {}", password);
//
        try {
            User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username is not correct or not available !!"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Password is not correct !!");
            }
        }
        catch (UsernameNotFoundException | BadCredentialsException e) {
            unsuccessfulAuthentication(request, response, e);
            return null;
        } catch (AuthenticationException e) {

            unsuccessfulAuthentication(request, response, new InternalAuthenticationServiceException(e.getMessage(), e));
            return null;
        }

        logger.info("This line is reached =====================|||||============");
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authRequest);

    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        String errorMessage;

        if (failed instanceof UsernameNotFoundException || failed instanceof BadCredentialsException) {
            errorMessage = failed.getMessage();
        } else {
            errorMessage = "Authentication failed: " + failed.getMessage();
        }

        response.getWriter().write("{\"success\": false, \"data\": \"" + errorMessage + "\"}");
    }
}
