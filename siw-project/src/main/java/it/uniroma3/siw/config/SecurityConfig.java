package it.uniroma3.siw.config;

import it.uniroma3.siw.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // Dice a Spring Security "quando qualcuno fa login, carica l'utente con questo service"
        provider.setUserDetailsService(customUserDetailsService);
        // Dice a Spring Security "confronta la password con BCrypt"
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Collega il provider che legge gli utenti dal DB
            .authenticationProvider(authenticationProvider())
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/libri", "/libri/**").permitAll()
                .requestMatchers("/login", "/registrazione").permitAll()
                .requestMatchers("/api/libri/**").permitAll()
                .requestMatchers("/api/orders/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/cart/**", "/orders/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )

            // LOGIN
            .formLogin(form -> form
                .loginPage("/login")              // La nostra pagina Thymeleaf
                .loginProcessingUrl("/login")      // Spring Security intercetta questo POST
                .usernameParameter("email")        // Usiamo email come "username"
                .passwordParameter("password")
                .successHandler(roleBasedSuccessHandler())  // Redirect diverso per ruolo
                .failureUrl("/login?error=true")
                .permitAll()
            )

            // LOGOUT
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/libri")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedSuccessHandler() {
        return (HttpServletRequest req, HttpServletResponse res, Authentication auth) -> {
            boolean isAdmin = auth.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            // Admin → pannello gestione libri
            // Cliente → catalogo
            res.sendRedirect(isAdmin ? "/admin/libri" : "/libri");
        };
    }
}