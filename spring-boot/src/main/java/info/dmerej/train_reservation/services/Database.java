package info.dmerej.train_reservation.services;

import info.dmerej.train_reservation.models.Seat;
import info.dmerej.train_reservation.models.Train;
import info.dmerej.train_reservation.repositories.SeatRepository;
import info.dmerej.train_reservation.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Database {
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private SeatRepository seatRepository;

    public List<String> getTrainNames() {
        return trainRepository.findAll().stream().map(Train::getName).collect(Collectors.toList());
    }

    public void deleteAll() {
        seatRepository.deleteAll();
        trainRepository.deleteAll();
    }

    public Train insertTrain(String name) {
        var entity = new Train();
        entity.setName(name);
        var saved = trainRepository.save(entity);
        return saved;
    }

    public Seat insertSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public Optional<Seat> getSeat(Train train, String number) {
        return seatRepository.findOneByTrainAndNumber(train, number);
    }

    public void bookSeat(Seat seat, String bookingReference) {
        if (seat.getBookingReference() != null && seat.getBookingReference() != bookingReference) {
            throw new RuntimeException("already booked");
        }
        seat.setBookingReference(bookingReference);
        seatRepository.save(seat);
    }

    public Optional<Train> getTrain(String name) {
        return trainRepository.findOneByName(name);
    }

    public void insertTrainWithSeats(String name, String... seatNumbers) {
        var train = insertTrain(name);
        for (var seatNumber : seatNumbers) {
            var seat = new Seat();
            seat.setNumber(seatNumber);
            seat.setTrain(train);
            seatRepository.save(seat);
        }
    }
}
