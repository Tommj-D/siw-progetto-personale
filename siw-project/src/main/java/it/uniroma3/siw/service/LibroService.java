package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.LibroRepository;

@Service
public class LibroService {

    private LibroRepository libroRepository;
    
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Libro> getAllLibri() {
        return libroRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Libro getLibroById(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    public List<Libro> searchLibri(String keyword) {
        return libroRepository.findByTitoloContainingIgnoreCaseOrAutoreContainingIgnoreCase(keyword, keyword);
    }
    
    @Transactional
    public Libro saveLibro(Libro book) {
        return libroRepository.save(book);
    }

    @Transactional
    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }

	public List<Libro> searchLibriByTitoloOrAutore(String query) {
		return libroRepository.findByTitoloContainingIgnoreCaseOrAutoreContainingIgnoreCase(query, query);
	}
	
	// UC4 e UC5: Salva un nuovo libro o ne aggiorna uno esistente
    public Libro salvaLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    // UC6: Cancella un libro dal database
    public void deleteLibroById(Long id) {
        libroRepository.deleteById(id);
    }
}