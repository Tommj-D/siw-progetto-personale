package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.VoceCarrello;

public interface VoceCarrelloRepository extends JpaRepository<VoceCarrello, Long> {
    // Elimina tutte le voci del carrello che contengono il libro con quell'id
    void deleteByBook_Id(Long libroId);

}