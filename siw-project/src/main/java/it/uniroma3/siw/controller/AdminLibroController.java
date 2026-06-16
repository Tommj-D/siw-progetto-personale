package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.service.LibroService;

@Controller
@RequestMapping("/admin/libri")
public class AdminLibroController {

    private final LibroService libroService;

    public AdminLibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public String showLibri(Model model) {
        // Usiamo getAllLibri() come definito nel LibroService
        model.addAttribute("libri", libroService.getAllLibri());
        return "admin/libri"; 
    }

    // UC4
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("libro", new Libro());
        return "admin/formLibro"; 
    }

    // UC5
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("libro", libroService.getLibroById(id));
        return "admin/formLibro"; 
    }

    // UC4 e UC5: Salva il libro
    @PostMapping
    public String salvaLibro(@ModelAttribute Libro libro) {
        libroService.salvaLibro(libro); 
        return "redirect:/admin/libri";
    }

    // UC6
    @GetMapping("/delete/{id}")
    public String cancellaLibro(@PathVariable Long id) {
        libroService.deleteLibroById(id);
        return "redirect:/admin/libri";
    }
}