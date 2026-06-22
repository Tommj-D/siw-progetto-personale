package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    public List<Libro> findByTitoloContainingIgnoreCaseOrAutoreContainingIgnoreCase(String titolo, String autore);
    public List<Libro> findTop10ByOrderByIdAsc();
}