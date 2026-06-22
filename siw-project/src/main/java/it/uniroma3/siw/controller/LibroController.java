package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siw.service.LibroService;

@Controller
public class LibroController {

    private final LibroService bookService;

    public LibroController(LibroService bookService) {
        this.bookService = bookService;
    }

    // UC1: Catalogo — i libri vengono caricati via fetch dal componente React, non dal model
    @GetMapping("/libri")
    public String showBooks(Model model) {
        return "catalogo";
    }

    // UC2: Dettaglio libro
    @GetMapping("/libri/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        model.addAttribute("libro", bookService.getLibroById(id));
        return "dettaglioLibro";
    }
}