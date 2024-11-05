package info.dmerej.train_reservation.repositories;

import info.dmerej.train_reservation.models.Seat;
import info.dmerej.train_reservation.models.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findOneByTrainAndNumber(Train train, String number);
}
