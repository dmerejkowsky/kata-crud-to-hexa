package info.dmerej.train_reservation;

import info.dmerej.train_reservation.models.Seat;
import info.dmerej.train_reservation.services.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        var train = database.getTrain("Express 2000");
        assertThat(train).isPresent();
    }

    @Test
    public void can_insert_an_available_seat() {
        var train = database.insertTrain("Orient Express");
        var seat = new Seat();
        seat.setTrain(train);
        seat.setNumber("2A");
        database.insertSeat(seat);

        var savedSeat = database.getSeat(train, "2A").get();
        assertThat(savedSeat.getBookingReference()).isNull();
    }

    @Test
    public void can_book_an_available_seat() {
        var train = database.insertTrain("Orient Express");
        var seat = new Seat();
        seat.setTrain(train);
        seat.setNumber("2A");
        database.insertSeat(seat);

        database.bookSeat(seat, "abc123");

        var savedSeat = database.getSeat(train, "2A").get();
        assertThat(savedSeat.getBookingReference()).isEqualTo("abc123");
    }

    @Test
    public void cannot_book_an_occupied_seat() {
        var train = database.insertTrain("Orient Express");
        var seat = new Seat();
        seat.setTrain(train);
        seat.setNumber("2A");
        seat.setBookingReference("abc123");
        database.insertSeat(seat);

        var alreadyBooked = database.getSeat(train, "2A").get();
        assertThatThrownBy(() ->
            database.bookSeat(alreadyBooked, "def456")
        ).isInstanceOf(RuntimeException.class);
    }

}