package info.dmerej.train_reservation.models;

import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue
    private Long id;
    private String number;
    private String bookingReference;

    @ManyToOne()
    @JoinColumn(name = "trainId", referencedColumnName = "id")
    private Train train;

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }
}
