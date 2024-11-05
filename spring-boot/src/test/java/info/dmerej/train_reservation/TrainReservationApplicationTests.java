package info.dmerej.train_reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.dmerej.train_reservation.models.Train;
import info.dmerej.train_reservation.services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
    private TrainService trainService;

    @BeforeEach
    void resetDatabase() {
        trainService.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void get_trains_when_database_is_empty() throws Exception {
        List<Train> returnedTrains = getTrains();

        assertThat(returnedTrains).isEmpty();
    }

    @Test
    void get_trains_when_two_trains_in_database() throws Exception {
        trainService.insertTrain("Express 2000");
        trainService.insertTrain("Orient Express");

        List<Train> returnedTrains = getTrains();
        Checkers.checkNames(returnedTrains, "Express 2000", "Orient Express");
    }

    private List<Train> getTrains() throws Exception {
        var response = mockMvc.perform(get("/trains"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();
        String json = response.getContentAsString();
        var objectMapper = new ObjectMapper();
        var collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Train.class);
        return objectMapper.readValue(json, collectionType);
    }


}
