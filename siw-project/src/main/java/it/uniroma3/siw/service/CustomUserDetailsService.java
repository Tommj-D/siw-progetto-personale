package it.uniroma3.siw.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email non trovata: " + email));

        return User.builder()
                .username(utente.getEmail()) 
                .password(utente.getPassword())
                .roles(utente.getRuolo()) 
                .build();
    }
}