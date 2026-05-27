package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Utente;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {
    public List<Ordine> findByUtente(Utente utente);
}