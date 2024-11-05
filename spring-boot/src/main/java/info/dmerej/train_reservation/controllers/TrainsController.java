package info.dmerej.train_reservation.controllers;

import info.dmerej.train_reservation.services.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class TrainsController {
    @Autowired
    private Database database;


    @GetMapping("/trains")
    List<TrainSummary> listTrains() {
        return database.getTrainNames().stream().map(TrainSummary::new).collect(Collectors.toList());
    }

    @PostMapping("/book")
    void book(@RequestBody BookingRequest request) {
        var trainName = request.train();
        var train = database.getTrain(trainName).get();
        for (var seatNumber : request.seats()) {
            var seat = database.getSeat(train, seatNumber).get();
            database.bookSeat(seat, request.booking_reference());
        }
    }
}
