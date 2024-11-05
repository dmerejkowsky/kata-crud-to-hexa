package info.dmerej.train_reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.dmerej.train_reservation.controllers.TrainSummary;
import info.dmerej.train_reservation.services.Database;
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


}
