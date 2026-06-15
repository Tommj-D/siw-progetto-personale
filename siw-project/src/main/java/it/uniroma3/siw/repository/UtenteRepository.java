package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Utente;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
}