package it.uniroma3.siw.controller;

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Necessario per far comunicare React
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN") // Solo ADMIN nell'area gestione
                .requestMatchers("/api/ordini/**").hasAnyRole("USER", "ADMIN") // Ordini per registrati
                .requestMatchers("/", "/api/libri/**", "/registrazione", "/login").permitAll() // Pagine pubbliche
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .defaultSuccessUrl("/", true) // Dopo il login riporta alla home
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}