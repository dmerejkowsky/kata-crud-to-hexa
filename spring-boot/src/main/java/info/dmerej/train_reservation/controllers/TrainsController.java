package info.dmerej.train_reservation.controllers;

import info.dmerej.train_reservation.models.Train;
import info.dmerej.train_reservation.services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrainsController {
    @Autowired
    private TrainService trainService;


    @GetMapping("/trains")
    List<Train> listTrains() {
        return trainService.list();
    }
}
