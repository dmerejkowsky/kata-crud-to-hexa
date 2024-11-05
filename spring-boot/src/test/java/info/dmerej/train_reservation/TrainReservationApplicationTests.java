package info.dmerej.train_reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import info.dmerej.train_reservation.controllers.BookingRequest;
import info.dmerej.train_reservation.controllers.TrainSummary;
import info.dmerej.train_reservation.services.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TrainReservationApplicationTests {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
        StandardCharsets.UTF_8
    );

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Database database;

    @BeforeEach
    void resetDatabase() {
        database.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void get_trains_when_database_is_empty() throws Exception {
        List<TrainSummary> returnedTrains = getTrains();

        assertThat(returnedTrains).isEmpty();
    }

    @Test
    void book_available_seat() throws Exception {
        database.insertTrainWithSeats("Express 2000", "1A", "2A");

        var request = new BookingRequest("Express 2000", List.of("2A"), "abc123");

        postBooking(request);
        // No assertions : we already check the status code in the above method
    }


    @Test
    void get_trains_when_two_trains_in_database() throws Exception {
        database.insertTrain("Express 2000");
        database.insertTrain("Orient Express");

        List<TrainSummary> returnedTrains = getTrains();
        var returnedNames = returnedTrains.stream().map(b -> b.name()).collect(Collectors.toList());
        assertThat(returnedNames).hasSameElementsAs(List.of("Express 2000", "Orient Express"));
    }

    private List<TrainSummary> getTrains() throws Exception {
        var response = mockMvc.perform(get("/trains"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
        String json = response.getContentAsString();
        var objectMapper = new ObjectMapper();
        var collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, TrainSummary.class);
        return objectMapper.readValue(json, collectionType);
    }

    private void postBooking(BookingRequest request) throws Exception {
        var mapper = new ObjectMapper();
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(request);

        mockMvc.perform(
                post("/book")
                    .contentType(APPLICATION_JSON_UTF8)
                    .content(requestJson))
            .andExpect(status().isOk());
    }
}
