package info.dmerej.train_reservation.controllers;

import info.dmerej.train_reservation.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class TrainsController {
    @Autowired
    private TrainService trainService;


    @GetMapping("/trains")
    List<TrainSummary> listTrains() {
        return trainService.getTrainNames().stream().map(TrainSummary::new).collect(Collectors.toList());
    }
}
