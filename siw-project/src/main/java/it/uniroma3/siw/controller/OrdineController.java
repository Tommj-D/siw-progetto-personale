package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import it.uniroma3.siw.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrdineController {

    @Autowired
    private OrdineService ordineService;

    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping("/orders")
    public String showOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {

        Utente utente = utenteRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        model.addAttribute("orders", ordineService.getOrdiniByUtente(utente));
        return "orders";
    }

    @PostMapping("/orders/checkout")
    public String checkout(@AuthenticationPrincipal UserDetails userDetails) {
        Utente utente = utenteRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();

        ordineService.creaOrdineDaCarrello(utente, utente.getCarrello());
        return "redirect:/orders";
    }
}