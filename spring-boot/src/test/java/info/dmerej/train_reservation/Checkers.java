package info.dmerej.train_reservation;

import info.dmerej.train_reservation.models.Train;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Checkers {
    static void checkNames(List<Train> returnedTrains, String... names) {
        var returnedNames = returnedTrains.stream().map(b -> b.getName()).collect(Collectors.toList());
        assertThat(returnedNames).hasSameElementsAs(List.of(names));
    }
}
