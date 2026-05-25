package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    public Utente findByEmail(String email);
}