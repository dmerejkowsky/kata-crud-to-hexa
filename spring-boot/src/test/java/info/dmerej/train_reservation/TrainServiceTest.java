package info.dmerej.train_reservation;

import info.dmerej.train_reservation.services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TrainServiceTest {
    @Autowired
    private TrainService trainService;

    @BeforeEach
    void resetDatabase() {
        trainService.deleteAll();
    }

    @Test
    public void can_insert_a_train() {
        trainService.insertTrain("Express 2000");

        var trains = trainService.getTrainNames();
        assertThat(trains).hasSameElementsAs(List.of("Express 2000"));
    }
}