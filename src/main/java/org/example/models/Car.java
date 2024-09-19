package org.example.models;

import java.time.LocalDateTime;

public class Car {

    public enum CarType {
        SEDAN,
        SUV,
        VAN
    }

    private String id;
    private String model;
    private CarType carType;
    private double pricePerDay;
    private boolean isAvailable;
    private LocalDateTime rentalStartDate;
    private LocalDateTime rentalEndDate;

    public Car(String id, String model, CarType carType, double pricePerDay) {
        this.id = id;
        this.model = model;
        this.carType = carType;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true; // All cars are available by default
    }

    public String getId() { return id; }
    public String getModel() { return model; }
    public CarType getCarType() { return carType; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) { this.isAvailable = available; }

    public LocalDateTime getRentalStartDate() { return rentalStartDate; }
    public LocalDateTime getRentalEndDate() { return rentalEndDate; }

    public void setRentalPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        this.rentalStartDate = startDate;
        this.rentalEndDate = endDate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", carType='" + carType + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", isAvailable=" + isAvailable +
                ", rentalStartDate=" + (rentalStartDate != null ? rentalStartDate : "N/A") +
                ", rentalEndDate=" + (rentalEndDate != null ? rentalEndDate : "N/A") +
                '}';
    }
}