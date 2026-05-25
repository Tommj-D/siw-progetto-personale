package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.model.Carrello;
import it.uniroma3.siw.repository.CarrelloRepository;

@Service
public class CarrelloService {

    private CarrelloRepository carrelloRepository;

    public CarrelloService(CarrelloRepository carrelloRepository) {
        this.carrelloRepository = carrelloRepository;
    }

    public List<Carrello> getAllCarrelli() {
        return carrelloRepository.findAll();
    }

    public Carrello getCarrelloById(Long id) {
        return carrelloRepository.findById(id).orElse(null);
    }

    @Transactional
    public Carrello saveCarrello(Carrello carrello) {
        return carrelloRepository.save(carrello);
    }
}