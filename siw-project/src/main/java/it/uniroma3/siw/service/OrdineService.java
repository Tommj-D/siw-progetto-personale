package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ordine;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.OrdineRepository;

@Service
public class OrdineService {

    private OrdineRepository ordineRepository;

    public OrdineService(OrdineRepository ordineRepository) {
        this.ordineRepository = ordineRepository;
    }

    public List<Ordine> getAllOrdini() {
        return ordineRepository.findAll();
    }

    public List<Ordine> getOrdiniByUtente(Utente utente) {
        return (List<Ordine>) ordineRepository.findByUtente(utente);
    }

    @Transactional
    public Ordine saveOrdine(Ordine ordine) {
        return ordineRepository.save(ordine);
    }
}