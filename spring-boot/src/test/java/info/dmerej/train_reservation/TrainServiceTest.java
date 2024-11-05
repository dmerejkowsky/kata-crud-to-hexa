package info.dmerej.train_reservation;

import info.dmerej.train_reservation.services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static info.dmerej.train_reservation.Checkers.checkNames;

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

        var trains = trainService.list();
        checkNames(trains, "Express 2000");
    }
}