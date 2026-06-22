package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.DettaglioOrdine;
import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.VoceCarrello;
import it.uniroma3.siw.repository.OrdineRepository;

@Service
public class OrdineService {

    private OrdineRepository ordineRepository;

    @Autowired
    private CarrelloService carrelloService;

    public OrdineService(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }

    @Transactional(readOnly = true)
    public List<Ordine> getAllOrdini() {
        return ordineRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Ordine> getOrdiniByUtente(Utente utente) {
        return ordineRepository.findByUtente(utente);
    }

    @Transactional
    public Ordine saveOrdine(Ordine ordine) {
        return ordineRepository.save(ordine);
    }

    @Transactional
    public Ordine creaOrdineDaCarrello(Utente utente, Carrello carrello) {
        if (carrello.getVoci().isEmpty()) {
            throw new RuntimeException("Il carrello è vuoto, impossibile effettuare l'ordine");
        }

        Ordine ordine = new Ordine();
        ordine.setUtente(utente);
        ordine.setDataOrdine(LocalDate.now());

        double totale = 0.0;
        for (VoceCarrello voce : carrello.getVoci()) {
            DettaglioOrdine dettaglio = new DettaglioOrdine();
            dettaglio.setLibro(voce.getBook());
            dettaglio.setQuantita(voce.getQuantita());
            dettaglio.setPrezzoAcquisto(voce.getBook().getPrezzo());
            ordine.aggiungiDettaglio(dettaglio);

            totale += voce.getBook().getPrezzo() * voce.getQuantita();
        }
        ordine.setTotale(totale);

        Ordine ordineSalvato = ordineRepository.save(ordine);

        // Svuota il carrello dopo aver creato l'ordine
        carrelloService.svuotaCarrello(carrello);

        return ordineSalvato;
    }
}