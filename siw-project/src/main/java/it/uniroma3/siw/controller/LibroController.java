package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

@Controller
public class LibroController {

    private final LibroService bookService;

    public LibroController(LibroService bookService) {
        this.bookService = bookService;
    }
    
    // UC1: Catalogo
    @GetMapping("/libri")
    public String showBooks(Model model) {
        // Usiamo "listaLibri" come nome standard per il template catalogo
        model.addAttribute("listaLibri", bookService.getAllLibri());
        return "catalogo";
    }

    // UC2: Dettaglio libro
    @GetMapping("/libri/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        model.addAttribute("libro", bookService.getLibroById(id));
        return "dettaglioLibro"; 
    }

    // UC3: Ricerca
    @GetMapping("/libri/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model) {
        // Usiamo lo stesso nome "listaLibri" così il template catalogo funziona in entrambi i casi
        model.addAttribute("listaLibri", bookService.searchLibri(keyword));
        return "catalogo";
    }

    // UC4: Mostra il form per aggiungere un nuovo libro
    @GetMapping("/admin/libri/new")
    public String showNewBookForm(Model model) {
        model.addAttribute("libro", new Libro()); // Uniformato in "libro"
        return "formLibro"; 
    }

    // UC5: Mostra il form per modificare un libro esistente
    @GetMapping("/admin/libri/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("libro", bookService.getLibroById(id)); // Uniformato in "libro"
        return "formLibro";
    }

    // UC4 e UC5: Gestisce il salvataggio dei dati inviati dal form (Insert o Update)
    @PostMapping("/admin/libri")
    public String saveBook(@ModelAttribute("libro") Libro libro) {
        bookService.saveLibro(libro);
        return "redirect:/libri";
    }

    // UC6: Cancellazione di un libro
    @GetMapping("/admin/libri/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteLibro(id);
        return "redirect:/libri";
    }
}