package info.dmerej.train_reservation.services;

import info.dmerej.train_reservation.models.Book;
import info.dmerej.train_reservation.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> list() {
        return bookRepository.findAll();
    }

    public Long add(Book book) {
        return bookRepository.save(book).getId();
    }

    public void deleteAll() {
        bookRepository.deleteAll();
    }

    public Optional<Book> getById(Long id) {
        return bookRepository.findById(id);
    }
}
