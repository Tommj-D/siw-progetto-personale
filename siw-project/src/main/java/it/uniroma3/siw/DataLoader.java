package it.uniroma3.siw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

    // iniezione del repository
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {

        // libro di test per riempire il database all'avvio
        Book b1 = new Book();
        b1.setTitle("Spring Boot");
        b1.setAuthor("Mario Rossi");
        b1.setPrice(30.0);

        // salvataggio nel DB
        bookRepository.save(b1);
    }
}