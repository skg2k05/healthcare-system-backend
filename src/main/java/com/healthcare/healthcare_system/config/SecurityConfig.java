package com.healthcare.healthcare_system.config;

import com.healthcare.healthcare_system.security.JwtFilter;
import com.healthcare.healthcare_system.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        JwtFilter jwtFilter = new JwtFilter(jwtUtil);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Public
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // Doctor endpoints
                        .requestMatchers("/api/appointments/doctor/**").hasRole("DOCTOR")
                        .requestMatchers(HttpMethod.PATCH, "/api/appointments/*/status").hasRole("DOCTOR")

                        // Citizen endpoints
                        .requestMatchers("/api/appointments/**").hasRole("CITIZEN")

                        // Everything else
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
