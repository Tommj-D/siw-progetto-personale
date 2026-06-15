package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import it.uniroma3.siw.service.CarrelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarrelloController {

    @Autowired
    private CarrelloService carrelloService;

    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping("/cart")
    public String showCart(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        Utente utente = utenteRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Carrello cart = utente.getCarrello();
        model.addAttribute("cart", cart);
        model.addAttribute("utente", utente);
        return "cart";
    }

    @PostMapping("/cart/add/{bookId}")
    public String addToCart(
            @PathVariable Long bookId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Utente utente = utenteRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        carrelloService.aggiungiAlCarrello(utente.getCarrello(), bookId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{voceId}")
    public String removeFromCart(
            @PathVariable Long voceId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Utente utente = utenteRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        carrelloService.rimuoviDalCarrello(utente.getCarrello(), voceId);
        return "redirect:/cart";
    }
}