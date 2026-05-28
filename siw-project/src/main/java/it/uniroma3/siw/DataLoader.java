package it.uniroma3.siw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Libro;
import it.uniroma3.siw.repository.LibroRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final LibroRepository bookRepository;

    // iniezione del repository
    public DataLoader(LibroRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {

        // libro di test per riempire il database all'avvio
        Libro b1 = new Libro();
        b1.setTitolo("Spring Boot");
        b1.setAutore("Mario Rossi");
        b1.setPrezzo(30.0);

        // salvataggio nel DB
        bookRepository.save(b1);
    }
}