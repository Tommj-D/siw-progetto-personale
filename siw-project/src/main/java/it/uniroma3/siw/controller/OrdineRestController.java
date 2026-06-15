package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.util.List;

import it.uniroma3.siw.model.DettaglioOrdine;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.LibroRepository;
import it.uniroma3.siw.repository.UtenteRepository;
import it.uniroma3.siw.service.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")

public class OrdineRestController {

    @Autowired
    private OrdineService ordineService;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @PostMapping("/checkout")
    public Ordine effettuaOrdine(
            @RequestBody Ordine ordine,
            @AuthenticationPrincipal UserDetails userDetails) {

        Utente utente = utenteRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        ordine.setUtente(utente);

        ordine.setDataOrdine(LocalDate.now());

        double totale = 0.0;
        for (DettaglioOrdine dettaglio : ordine.getDettagli()) {
            var libroVero = libroRepository.findById(dettaglio.getLibro().getId())
                    .orElseThrow(() -> new RuntimeException("Libro non trovato"));
            dettaglio.setPrezzoAcquisto(libroVero.getPrezzo());
            totale += libroVero.getPrezzo() * dettaglio.getQuantita();
            dettaglio.setOrdine(ordine);
        }
        ordine.setTotale(totale);

        return ordineService.saveOrdine(ordine);
    }

    @GetMapping
    public List<Ordine> getOrdini(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return List.of();
        Utente utente = utenteRepository.findByEmail(userDetails.getUsername())
                .orElseThrow();
        return ordineService.getOrdiniByUtente(utente);
    }
}