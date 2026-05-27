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
public class BookController {

    private final LibroService bookService;

    public BookController(LibroService bookService) {
        this.bookService = bookService;
    }
    
    // UC1: Catalogo
    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllLibri());
        return "books";
    }

    // UC2: Dettaglio libro
    @GetMapping("/books/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getLibroById(id));
        return "bookDetails"; 
    }

    // UC3: Ricerca
    @GetMapping("/books/search")
    public String searchBooks(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("books", bookService.searchLibri(keyword));
        return "books"; // Riusiamo la stessa pagina del catalogo per mostrare i risultati
    }

    // UC4: Mostra il form per aggiungere un nuovo libro
    @GetMapping("/admin/books/new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Libro());
        return "bookForm"; 
    }

    // UC5: Mostra il form per modificare un libro esistente
    @GetMapping("/admin/books/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getLibroById(id));
        return "bookForm";
    }

    // UC4 e UC5: Gestisce il salvataggio dei dati inviati dal form (Insert o Update)
    @PostMapping("/admin/books")
    public String saveBook(@ModelAttribute Libro book) {
        bookService.saveLibro(book);
        return "redirect:/books"; // Dopo il salvataggio, rimanda l'utente al catalogo
    }

    // UC6: Cancellazione di un libro
    @GetMapping("/admin/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteLibro(id);
        return "redirect:/books";
    }
}