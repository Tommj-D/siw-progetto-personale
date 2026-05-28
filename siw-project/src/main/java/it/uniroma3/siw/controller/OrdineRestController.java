package it.uniroma3.siw.controller;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.DettaglioOrdine;
import it.uniroma3.siw.service.OrdineService;
import it.uniroma3.siw.repository.LibroRepository;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://localhost:3000"}) // Sblocca CORS per React
public class OrdineRestController {

    private final OrdineService ordineService;
    private final LibroRepository libroRepository; // Ci serve per recuperare i prezzi veri dei libri

    public OrdineRestController(OrdineService ordineService, LibroRepository libroRepository) {
        this.ordineService = ordineService;
        this.libroRepository = libroRepository;
    }

    @PostMapping("/checkout")
    public Ordine effettuaOrdine(@RequestBody Ordine ordine) {
        // 1. Impostiamo la data dell'ordine al momento del click
        ordine.setDataOrdine(LocalDate.now());

        // 2. Calcoliamo il totale blindato nel backend per sicurezza
        Double totaleCalcolato = 0.0;
        
        for (DettaglioOrdine dettaglio : ordine.getDettagli()) {
            var libroVero = libroRepository.findById(dettaglio.getLibro().getId())
                    .orElseThrow(() -> new RuntimeException("Libro non trovato"));
            
            // Usiamo il setPrezzoAcquisto che hai definito tu!
            dettaglio.setPrezzoAcquisto(libroVero.getPrezzo());
            
            totaleCalcolato += libroVero.getPrezzo() * dettaglio.getQuantita();
            dettaglio.setOrdine(ordine);
        }
        
        ordine.setTotale(totaleCalcolato);

        // 3. Salviamo l'ordine nel database
        return ordineService.saveOrdine(ordine);
    }
}