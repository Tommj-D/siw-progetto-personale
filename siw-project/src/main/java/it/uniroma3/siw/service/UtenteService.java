package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.RegistrationForm;
import it.uniroma3.siw.repository.CarrelloRepository;
import it.uniroma3.siw.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Utente registraCliente(RegistrationForm form) {
        // 1. Crea un carrello vuoto per il nuovo cliente
        Carrello carrello = new Carrello();
        carrello = carrelloRepository.save(carrello);

        // 2. Crea l'utente con password cifrata e ruolo USER
        Utente utente = new Utente();
        utente.setNome(form.getNome());
        utente.setEmail(form.getEmail());
        utente.setPassword(passwordEncoder.encode(form.getPassword()));
        utente.setRuolo("USER");
        utente.setCarrello(carrello);

        return utenteRepository.save(utente);
    }

    @Transactional
    public Utente registraAdmin(String nome, String email, String password) {
        Utente admin = new Utente();
        admin.setNome(nome);
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRuolo("ADMIN");
        // L'admin non ha carrello
        return utenteRepository.save(admin);
    }

    @Transactional(readOnly = true)
    public boolean emailEsiste(String email) {
        return utenteRepository.findByEmail(email).isPresent();
    }

    @Transactional(readOnly = true)
    public Optional<Utente> findByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }
}