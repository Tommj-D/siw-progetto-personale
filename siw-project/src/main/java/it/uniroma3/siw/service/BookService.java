package it.uniroma3.siw.service;

import java.util.List;


import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.repository.BookRepository;

@Service
public class BookService {

    private BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository) {
    	this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}