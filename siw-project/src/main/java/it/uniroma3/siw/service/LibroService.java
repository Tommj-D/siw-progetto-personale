package it.uniroma3.siw.service;

import it.uniroma3.siw.model.DettaglioOrdine;
import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.DettaglioOrdineRepository;
import it.uniroma3.siw.repository.LibroRepository;
import it.uniroma3.siw.repository.VoceCarrelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private VoceCarrelloRepository voceCarrelloRepository;

    @Autowired
    private DettaglioOrdineRepository dettaglioOrdineRepository;

    @Transactional(readOnly = true)
    public List<Libro> getAllLibri() {
        return libroRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public List<Libro> getTopTenLibri() {
        return libroRepository.findTop10ByOrderByIdAsc();
    }

    @Transactional(readOnly = true)
    public Libro getLibroById(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Libro> searchLibri(String keyword) {
        return libroRepository
                .findByTitoloContainingIgnoreCaseOrAutoreContainingIgnoreCase(keyword, keyword);
    }

    @Transactional(readOnly = true)
    public List<Libro> searchLibriByTitoloOrAutore(String query) {
        return searchLibri(query);
    }

    @Transactional
    public Libro salvaLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Transactional
    public void deleteLibroById(Long id) {
        // 1: rimuove il libro da tutti i carrelli degli utenti
        voceCarrelloRepository.deleteByBook_Id(id);

        // 2: scollega il libro dagli ordini già effettuati
        // (non cancella gli ordini, solo il riferimento al libro)
        List<DettaglioOrdine> dettagli = dettaglioOrdineRepository.findByLibro_Id(id);
        for (DettaglioOrdine d : dettagli) {
            d.setLibro(null);
        }
        dettaglioOrdineRepository.saveAll(dettagli);

        // 3: eliminazione del libro
        libroRepository.deleteById(id);
    }
}