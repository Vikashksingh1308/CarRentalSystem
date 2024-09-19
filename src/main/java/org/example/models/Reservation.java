package org.example.models;

import java.time.LocalDate;

public class Reservation {
    private final String reservationId;
    private final Customer customer;
    private final Car car;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Reservation(String reservationId, Customer customer, Car car, LocalDate startDate, LocalDate endDate) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter for reservationId
    public String getReservationId() {
        return reservationId;
    }

    // Getter for customer
    public Customer getCustomer() {
        return customer;
    }

    // Getter for car
    public Car getCar() {
        return car;
    }

    // Getter for startDate
    public LocalDate getStartDate() {
        return startDate;
    }

    // Getter for endDate
    public LocalDate getEndDate() {
        return endDate;
    }

    // Override toString() to include reservation details
    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", customer=" + customer +
                ", car=" + car +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
