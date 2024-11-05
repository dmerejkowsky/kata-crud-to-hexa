package info.dmerej.train_reservation.services;

import info.dmerej.train_reservation.models.Train;
import info.dmerej.train_reservation.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainService {
    @Autowired
    private TrainRepository trainRepository;

    public List<String> getTrainNames() {
        return trainRepository.findAll().stream().map(Train::getName).collect(Collectors.toList());
    }

    public void deleteAll() {
        trainRepository.deleteAll();
    }

    public Optional<Train> getById(Long id) {
        return trainRepository.findById(id);
    }

    public Long insertTrain(String name) {
        var entity = new Train();
        entity.setName(name);
        var saved = trainRepository.save(entity);
        return saved.getId();
    }
}
