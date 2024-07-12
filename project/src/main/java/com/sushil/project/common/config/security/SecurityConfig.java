package com.sushil.project.common.config.security;


import com.sushil.project.auth.application.serviceImpl.AuthCustomService;
import com.sushil.project.auth.application.serviceImpl.CustomAccessDeniedHandler;
import com.sushil.project.auth.application.serviceImpl.CustomAuthenticationFilter;
import com.sushil.project.auth.application.serviceImpl.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthCustomService userDetailService;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler){
        this.accessDeniedHandler = customAccessDeniedHandler;
    }

    private AuthenticationConfiguration conf;


    @Bean
    public static PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter(authenticationManager(conf));
        //filter.setAuthenticationFailureHandler();
        filter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        return filter;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//       CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(conf));
//       customAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
//        //customAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
       return http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(
                       auth -> auth
                               .requestMatchers("/actuator/*").permitAll()
                               .requestMatchers("/favicon.*", "/js/*", "/css/*", "/error", "/register", "/", "/login_req").permitAll()
                               .anyRequest().authenticated()
               )
               .exceptionHandling(exceptionHandling -> exceptionHandling
                       .accessDeniedHandler(accessDeniedHandler)
               )
               .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
               .formLogin().disable()
               .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                       .logoutUrl("/logout")
                       .logoutSuccessUrl("/")
                       .invalidateHttpSession(true)
                       .clearAuthentication(true)
               )
               .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailService;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}

//               .csrf(csrf -> csrf.disable())
//.ignoringRequestMatchers("/login_req")


//               .formLogin(httpSecurityFormLoginConfigurer -> {
//                       httpSecurityFormLoginConfigurer
//                       .loginPage("/")
//                       .loginProcessingUrl("/login_re")
//                       .successHandler(new CustomAuthenticationSuccessHandler())
//                       .permitAll();
//                       })