package info.dmerej.train_reservation.repositories;

import info.dmerej.train_reservation.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    Optional<Train> findOneByName(String name);
}
