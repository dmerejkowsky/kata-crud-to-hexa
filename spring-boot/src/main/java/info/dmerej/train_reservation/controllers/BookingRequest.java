package info.dmerej.train_reservation.controllers;

import java.util.List;

public record BookingRequest(String train, List<String> seats, String booking_reference) {
}
