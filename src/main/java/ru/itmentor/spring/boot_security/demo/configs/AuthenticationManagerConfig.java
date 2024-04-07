package ru.itmentor.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itmentor.spring.boot_security.demo.service.CustomUserDetailService;

@Configuration
public class AuthenticationManagerConfig {
    private final CustomUserDetailService customUserDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationManagerConfig(
            CustomUserDetailService customUserDetailsService,
            BCryptPasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }
}
