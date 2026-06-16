package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/registrazione")
    public String showRegistrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "register";
    }

    @PostMapping("/registrazione")
    public String processaRegistrazione(@ModelAttribute Utente utente, Model model) {
        if (utenteService.emailEsiste(utente.getEmail())) {
            model.addAttribute("errore", "Questa email è già registrata.");
            return "register";
        }
        utenteService.registraCliente(utente);
        return "redirect:/login";
    }
}