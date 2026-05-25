package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.service.CarrelloService;

@Controller
public class CarrelloController {

    private final CarrelloService carrelloService;

    public CarrelloController(CarrelloService carrelloService) {
        this.carrelloService = carrelloService;
    }

    // Mostra il contenuto del carrello
    @GetMapping("/cart")
    public String showCart(Model model) {
        Carrello cart = carrelloService.getCarrelloById(1L);
        model.addAttribute("cart", cart);
        return "cart";
    }

    // UC7: Aggiunta di un libro al carrello (simulazione rotta)
    @GetMapping("/cart/add/{bookId}")
    public String addToCart(@PathVariable Long bookId) {
        return "redirect:/cart";
    }
}