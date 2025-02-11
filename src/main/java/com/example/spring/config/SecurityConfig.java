package com.example.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;  // SecurityFilterChain import
import org.springframework.web.servlet.config.annotation.EnableWebMvc;  // HandlerMappingIntrospector import
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;  // EnableWebMvc import

@Configuration
@EnableWebSecurity
@EnableWebMvc  // Spring MVC 설정 활성화
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests -> 
                authorizeRequests
                    .requestMatchers("/login", "/register").permitAll()  // 로그인, 회원가입 경로는 누구나 접근 가능
                    .anyRequest().authenticated()  // 나머지 경로는 인증 필요
            )
            .formLogin(form -> 
                form
                    .loginPage("/login")
                    .permitAll()
            )
            .logout(logout -> 
                logout
                    .permitAll()
            );
        return http.build();  // HttpSecurity 빌드를 마무리
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화 설정
    }

    // mvcHandlerMappingIntrospector 빈 추가
    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }
}
