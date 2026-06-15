package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

@RestController
@RequestMapping("/api/libri")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LibroRestController {

    private final LibroService libroService;

    public LibroRestController(LibroService bookService) {
        this.libroService = bookService;
    }

    // Risponde a: GET http://localhost:8080/api/books
    @GetMapping
    public List<Libro> getAllLibri() {
        return libroService.getAllLibri();
    }

    // Risponde a: GET http://localhost:8080/api/books/1
    @GetMapping("/{id}")
    public Libro getLibroById(@PathVariable Long id) {
        return libroService.getLibroById(id);
    }
    
    // Risponde a: GET http://localhost:8080/api/libri/search?query=parola
    @GetMapping("/search")
    public List<Libro> searchLibri(@RequestParam String query) {
        return libroService.searchLibriByTitoloOrAutore(query);
    }
}
