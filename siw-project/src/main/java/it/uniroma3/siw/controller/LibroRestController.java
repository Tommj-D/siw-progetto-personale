package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

@RestController
@RequestMapping("/api/libri")
public class LibroRestController {

    private final LibroService libroService;

    public LibroRestController(LibroService bookService) {
        this.libroService = bookService;
    }

    // Risponde a: GET /api/libri
    @GetMapping
    public List<Libro> getAllLibri() {
        return libroService.getAllLibri();
    }

    // Risponde a: GET /api/libri/homepage
    @GetMapping("/homepage")
    public List<Libro> getLibriHomepage() {
        return libroService.getTopTenLibri();
    }

    // Risponde a: GET /api/libri/search?query=parola
    @GetMapping("/search")
    public List<Libro> searchLibri(@RequestParam String query) {
        return libroService.searchLibriByTitoloOrAutore(query);
    }

    // Risponde a: GET /api/libri/1
    @GetMapping("/{id}")
    public Libro getLibroById(@PathVariable Long id) {
        return libroService.getLibroById(id);
    }
}