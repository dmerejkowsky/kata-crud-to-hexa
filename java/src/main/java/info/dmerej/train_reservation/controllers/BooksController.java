package info.dmerej.train_reservation.controllers;

import info.dmerej.train_reservation.models.Book;
import info.dmerej.train_reservation.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BooksController {
    @Autowired
    private BookService bookService;


    @GetMapping("/books")
    List<Book> listBooks() {
        return bookService.list();
    }
}
