package com.github.EmilyMartins.imageliteapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.github.EmilyMartins.imageliteapi.config.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public com.github.EmilyMartins.imageliteapi.config.filter.JwtFilter jwtFilter(com.github.EmilyMartins.imageliteapi.application.jwt.JwtService jwtService, com.github.EmilyMartins.imageliteapi.domain.service.UserService userService){
        return new JwtFilter(jwtService, userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, com.github.EmilyMartins.imageliteapi.config.filter.JwtFilter jwtFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/v1/users/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET, "/v1/images/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
        cors.registerCorsConfiguration("/**", config);

        return cors;
    }
}