package info.dmerej.train_reservation;

import info.dmerej.train_reservation.models.Seat;
import info.dmerej.train_reservation.services.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DatabaseTest {
    @Autowired
    private Database database;

    @BeforeEach
    void resetDatabase() {
        database.deleteAll();
    }

    @Test
    public void can_insert_a_train() {
        database.insertTrain("Express 2000");

        var trains = database.getTrainNames();
        assertThat(trains).hasSameElementsAs(List.of("Express 2000"));
    }

    @Test
    public void can_insert_a_seat() {
        var train = database.insertTrain("Orient Express");
        var seat = new Seat();
        seat.setTrain(train);
        seat.setNumber("2A");
        seat.setBookingReference("abc123");
        database.insertSeat(seat);
    }
}