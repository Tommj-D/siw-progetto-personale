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

    public List<Libro> getAllBooks() {
        return bookRepository.findAll();
    }

    public Libro getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Libro> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
    }
    
    @Transactional
    public Libro saveBook(Libro book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}