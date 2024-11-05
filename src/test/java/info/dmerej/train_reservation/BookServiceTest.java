package info.dmerej.train_reservation;

import info.dmerej.train_reservation.models.Book;
import info.dmerej.train_reservation.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void gettingAllBooksIsEmptyAtFirst() {
        List<Book> books = bookService.list();

        assertThat(books).isEmpty();
    }
}