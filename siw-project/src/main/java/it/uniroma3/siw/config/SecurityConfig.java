package it.uniroma3.siw.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            )
            
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/", "/index.html", "/static/**", "/favicon.ico", "/manifest.json").permitAll()
            	    .requestMatchers("/libro/**", "/ordini").permitAll()
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
                .loginPage("/login")              // La pagina Thymeleaf
                .loginProcessingUrl("/login")      // Spring Security intercetta questo POST
                .usernameParameter("email")        // Usiamo email come "username"
                .passwordParameter("password")
                .successHandler(roleBasedSuccessHandler())  // Redirect diverso per ruolo
                .failureUrl("/login?error=true")
                .permitAll()
            )

            .logout(logout -> logout
            	    .logoutUrl("/logout")
            	    .invalidateHttpSession(true)
            	    .deleteCookies("JSESSIONID")
            	    .logoutSuccessUrl("/")
            	    .permitAll()
            	);

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration();
    	configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
    	configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    	configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    	configuration.setAllowCredentials(true);

    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", configuration);
    	return source;
    }
    
    @Bean
    public AuthenticationSuccessHandler roleBasedSuccessHandler() {
        return (HttpServletRequest req, HttpServletResponse res, Authentication auth) -> {
            boolean isAdmin = auth.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
            // Se è Admin va al pannello di gestione Thymeleaf (8080)
            // Se è Cliente (USER) viene reindirizzato alla vetrina React (3000)
            res.sendRedirect(isAdmin ? "/admin/libri" : "/");
        };
    }
    
}