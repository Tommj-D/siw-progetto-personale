package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Transactional(readOnly = true)
    public List<Libro> getAllLibri() {
        return libroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Libro getLibroById(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Libro> searchLibri(String keyword) {
        return libroRepository
                .findByTitoloContainingIgnoreCaseOrAutoreContainingIgnoreCase(keyword, keyword);
    }

    @Transactional(readOnly = true)
    public List<Libro> searchLibriByTitoloOrAutore(String query) {
        return searchLibri(query);
    }
    
    @Transactional
    public Libro salvaLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Transactional
    public void deleteLibroById(Long id) {
        libroRepository.deleteById(id);
    }
}