package it.uniroma3.siw.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/favicon.ico").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                    .requestMatchers("/libri", "/libri/**").permitAll()
                    .requestMatchers("/login", "/registrazione").permitAll()
                    .requestMatchers("/api/libri/**").permitAll()
                    .requestMatchers("/cart/**", "/orders/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(roleBasedSuccessHandler())
                .failureUrl("/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedSuccessHandler() {
        return (HttpServletRequest req, HttpServletResponse res, Authentication auth) -> {
            boolean isAdmin = auth.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            res.sendRedirect(isAdmin ? "/admin/libri" : "/libri");
        };
    }
}