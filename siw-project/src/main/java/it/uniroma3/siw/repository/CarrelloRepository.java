package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Carrello;

public interface CarrelloRepository extends JpaRepository<Carrello, Long> {
}