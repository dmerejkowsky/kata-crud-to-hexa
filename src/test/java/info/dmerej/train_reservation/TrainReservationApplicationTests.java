package info.dmerej.train_reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.dmerej.train_reservation.models.Book;
import info.dmerej.train_reservation.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TrainReservationApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private BookService bookService;

    private static void checkNames(List<Book> returnedBooks, String... names) {
        var returnedNames = returnedBooks.stream().map(b -> b.getName()).collect(Collectors.toList());
        assertThat(returnedNames).hasSameElementsAs(List.of(names));
    }

    @BeforeEach
    void resetDatabase() {
        bookService.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getBooksWhenEmpty() throws Exception {
        var response = mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
        List<Book> returnedBooks = getBooks();
        assertThat(returnedBooks).isEmpty();
    }

    @Test
    void getBooksWhenTwoBooksInDatabase() throws Exception {
        var book1 = new Book("Axiom's End");
        var book2 = new Book("Truth of the Divine");
        bookService.add(book1);
        bookService.add(book2);

        List<Book> returnedBooks = getBooks();
        checkNames(returnedBooks, book1.getName(), book2.getName());
    }

    private List<Book> getBooks() throws Exception {
        var response = mockMvc.perform(get("/books"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
        String json = response.getContentAsString();
        var objectMapper = new ObjectMapper();
        var collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Book.class);
        return objectMapper.readValue(json, collectionType);
    }


}
