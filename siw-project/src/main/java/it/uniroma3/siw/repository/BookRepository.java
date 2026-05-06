package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siw.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}