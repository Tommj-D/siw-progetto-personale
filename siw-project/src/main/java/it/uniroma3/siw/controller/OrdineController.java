package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.service.OrdineService;

@Controller
public class OrdineController {

    private final OrdineService ordineService;

    public OrdineController(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    // UC9: Visualizzazione degli ordini passati dell'utente
    @GetMapping("/orders")
    public String showOrders(Model model) {
        model.addAttribute("orders", ordineService.getAllOrdini());
        return "orders";
    }

    // UC8: Effettuare un ordine (checkout dal carrello)
    @PostMapping("/orders/checkout")
    public String checkout() {
        return "redirect:/orders";
    }
}