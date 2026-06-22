package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.model.VoceCarrello;
import it.uniroma3.siw.repository.CarrelloRepository;
import it.uniroma3.siw.repository.LibroRepository;
import it.uniroma3.siw.repository.VoceCarrelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private VoceCarrelloRepository voceCarrelloRepository;

    @Transactional(readOnly = true)
    public Carrello getCarrelloById(Long id) {
        return carrelloRepository.findById(id).orElse(null);
    }

    @Transactional
    public Carrello saveCarrello(Carrello carrello) {
        return carrelloRepository.save(carrello);
    }

    @Transactional
    public void aggiungiAlCarrello(Carrello carrello, Long libroId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro non trovato: " + libroId));

        // Cerca se il libro è già nel carrello
        List<VoceCarrello> voci = carrello.getVoci();
        for (VoceCarrello voce : voci) {
            if (voce.getBook().getId().equals(libroId)) {
                voce.setQuantita(voce.getQuantita() + 1);
                voceCarrelloRepository.save(voce);
                return;
            }
        }

        VoceCarrello nuovaVoce = new VoceCarrello();
        nuovaVoce.setBook(libro);
        nuovaVoce.setQuantita(1);
        nuovaVoce.setCarrello(carrello);
        voceCarrelloRepository.save(nuovaVoce);
    }

    @Transactional
    public void rimuoviDalCarrello(Carrello carrello, Long voceId) {
        voceCarrelloRepository.deleteById(voceId);
    }
    
    @Transactional
    public void svuotaCarrello(Carrello carrello) {
        voceCarrelloRepository.deleteAll(carrello.getVoci());
        carrello.getVoci().clear();
    }
}