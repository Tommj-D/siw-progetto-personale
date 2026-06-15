package it.uniroma3.siw.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@RestController
public class AuthController {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/registrazione")
    public String registraCliente(@RequestBody Utente nuovoUtente) {
        // Controlla se la mail esiste già
        if (utenteRepository.findByEmail(nuovoUtente.getEmail()).isPresent()) {
            return "Errore: Email già in uso!";
        }

        // Cripta la password, imposta il ruolo USER e salva nel DB
        nuovoUtente.setPassword(passwordEncoder.encode(nuovoUtente.getPassword()));
        nuovoUtente.setRuolo("USER"); 
        utenteRepository.save(nuovoUtente);

        return "Registrazione completata con successo!";
    }
}