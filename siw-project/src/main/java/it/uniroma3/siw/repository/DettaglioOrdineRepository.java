package it.uniroma3.siw.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.DettaglioOrdine;

public interface DettaglioOrdineRepository extends JpaRepository<DettaglioOrdine, Long> {
    // Trova tutti i dettagli d'ordine che contengono quel libro
    List<DettaglioOrdine> findByLibro_Id(Long libroId);

}