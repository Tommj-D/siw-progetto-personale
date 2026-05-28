package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    // Cambiato da "Title" a "Titolo" e da "Author" a "Autore"
    public List<Libro> findByTitoloContainingIgnoreCaseOrAutoreContainingIgnoreCase(String titolo, String autore);

}