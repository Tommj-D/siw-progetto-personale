package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.BookService;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    
    //ci da il nome del file
    @GetMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books.html";
    }
}