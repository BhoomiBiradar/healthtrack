package com.pes.healthtrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize

                .requestMatchers(
                    "/register",        
                    "/register/**",    
                    "/login",          
                    "/login/**",       
                    "/css/**",          
                    "/js/**",           
                    "/images/**"        
                ).permitAll()

                .requestMatchers("/patient/**").hasRole("PATIENT") 
                .requestMatchers("/doctor/**").hasRole("DOCTOR")   
                .anyRequest().authenticated() 
            )
            .formLogin(form -> form
                .loginPage("/login")
                .permitAll() 
                .defaultSuccessUrl("/dashboard", true) 
                .failureUrl("/login?error=true")      
            )
            .logout(logout -> logout
                .permitAll() 
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/login?logout=true") 
            );
        return http.build();
    }
}