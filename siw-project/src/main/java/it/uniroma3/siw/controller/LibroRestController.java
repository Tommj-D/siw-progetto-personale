package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class LibroRestController {

    private final LibroService bookService;

    public LibroRestController(LibroService bookService) {
        this.bookService = bookService;
    }

    // Risponde a: GET http://localhost:8080/api/books
    @GetMapping
    public List<Libro> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Risponde a: GET http://localhost:8080/api/books/1
    @GetMapping("/{id}")
    public Libro getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }
}