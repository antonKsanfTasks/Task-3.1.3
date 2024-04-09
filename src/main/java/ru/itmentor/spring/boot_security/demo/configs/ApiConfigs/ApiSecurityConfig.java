package ru.itmentor.spring.boot_security.demo.configs.ApiConfigs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@Order(1)
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
    private final ApiCustomAuthenticationHandler apiCustomAuthenticationHandler;

    @Autowired
    public ApiSecurityConfig(ApiCustomAuthenticationHandler apiCustomAuthenticationHandler) {
        this.apiCustomAuthenticationHandler = apiCustomAuthenticationHandler;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/api/**")  // Ограничиваем эту конфигурацию API-путями
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/auth/login")  // URL, который обрабатывает POST-запросы для аутентификации
                .successHandler(apiCustomAuthenticationHandler)
                .failureHandler(apiCustomAuthenticationHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                })
                .and()
                .httpBasic()
                .and()
                .csrf().disable();  // Если вы не используете CSRF, лучше его отключить для API
    }

}
