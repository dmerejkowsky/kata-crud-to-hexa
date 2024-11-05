package info.dmerej.train_reservation;

import info.dmerej.train_reservation.models.Book;
import info.dmerej.train_reservation.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @BeforeEach
    void resetDatabase() {
        bookService.deleteAll();
    }

    @Test
    public void gettingAllBooksIsEmptyAtFirst() {
        List<Book> books = bookService.list();

        assertThat(books).isEmpty();
    }

    @Test
    public void canInsertABook() {
        var book = new Book("Axiom's End");

        bookService.add(book);

        List<Book> books = bookService.list();
        assertThat(books).hasSize(1);
        var savedBook = books.getFirst();
        assertThat(savedBook.getName()).isEqualTo("Axiom's End");
    }

    @Test
    public void canUpdateABook() {
        var book = new Book("Axiom's End");
        var id = bookService.add(book);

        var savedBook = bookService.getById(id).get();
        savedBook.setName("Other Name");
        bookService.add(savedBook);

        var updatedBook = bookService.getById(id).get();
        assertThat(updatedBook.getName()).isEqualTo("Other Name");
    }
}