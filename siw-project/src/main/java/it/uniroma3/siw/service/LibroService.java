package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.LibroRepository;

@Service
public class LibroService {

    private LibroRepository bookRepository;
    
    public LibroService(LibroRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Libro> getAllLibri() {
        return bookRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Libro getLibroById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Libro> searchLibri(String keyword) {
        return bookRepository.findByTitoloContainingIgnoreCaseOrAutoreContainingIgnoreCase(keyword, keyword);
    }
    
    @Transactional
    public Libro saveLibro(Libro book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteLibro(Long id) {
        bookRepository.deleteById(id);
    }
}