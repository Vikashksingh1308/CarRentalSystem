package org.example.services;

import org.example.models.Car;
import org.example.models.Customer;
import org.example.models.Reservation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarService {
    private List<Car> carInventory;
    private Map<Car.CarType, Integer> availableCarByType;
    private List<Reservation> reservations;
    private List<Customer> customerList = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    public int getNumberOfCustomers() {
        return customerList.size();
    }

    public CarService() {
        this.carInventory = new ArrayList<>();
        this.availableCarByType = new HashMap<Car.CarType, Integer>();
        this.reservations = new ArrayList<>();
    }

    // Add car to the inventory
    public void addCar(Car car){
        carInventory.add(car);
        availableCarByType.put(car.getCarType(), availableCarByType.getOrDefault(car.getCarType(), 0) + 1);
        System.out.println("Car added: " + car.getModel());
    }

    // Show all available cars
    public void showAvailableCars(String model, String carType, LocalDate startDate, LocalDate endDate) {
        List<Car> availableCars = getAvailableCarsForDates(model, carType, startDate, endDate);
        if(!availableCars.isEmpty()) {
            System.out.println("Available cars:");
            for(Car car : availableCars) {
                System.out.println(car.toString());
            }
        } else {
            System.out.println("No cars available during the selected period");
        }
    }

    // Get available cars for a date range based on reservations
    public List<Car> getAvailableCarsForDates(String model, String carType, LocalDate startDate, LocalDate endDate){
        List<Car> availableCarsForDates = new ArrayList<>();

        for (Car car : carInventory) {
            // Filter by car type if specified
            if (carType != null && !car.getCarType().name().equalsIgnoreCase(carType)) {
                continue;
            }

            // Filter by model if specified
            if (model != null && !car.getModel().equalsIgnoreCase(model)) {
                continue;
            }

            if (car.isAvailable() && isCarAvailableForDates(car, startDate, endDate)) {
                availableCarsForDates.add(car);
            }
        }
        return availableCarsForDates;
    }

    // Check if a car is available for the given dates
    private boolean isCarAvailableForDates(Car car, LocalDate startDate, LocalDate endDate) {
        // If no dates are provided, we assume the car is available
        if (startDate == null || endDate == null) {
            return true; // Treat as available if no date range is specified
        }

        for (Reservation reservation : reservations) {
            if(reservation.getCar().getId().equals(car.getId())){
                // Check for date overlap with reservation dates
                if(!(endDate.isBefore(reservation.getStartDate()) || startDate.isAfter(reservation.getEndDate()))) {
                    return false;
                }
            }
        }
        return true; // No overlap, car is available
    }

    // Rent car and record reservation
    public void rentCar(String carId, Customer customer, LocalDate startDate, LocalDate endDate) {
        for (Car car: carInventory){
            if(car.getId().equals(carId) && car.isAvailable()) {
                //Check for date overlap with existing reservations
                if(isCarAvailableForDates(car, startDate, endDate)){
                    //Create reservation record
                    String reservationId = "R" + (reservations.size()+1); // Simple ID generation
                    Reservation reservation = new Reservation(reservationId, customer, car, startDate, endDate);
                    reservations.add(reservation);

                    // Mark car as unavailable
                    car.setAvailable(false);
                    car.setRentalPeriod(startDate.atStartOfDay(), endDate.atStartOfDay());

                    System.out.println("Car rented: " + car.getModel() + " for " + ChronoUnit.DAYS.between(startDate, endDate) + " days.");
                    System.out.println("reservation created: " + reservation);
                    return;
                } else {
                    System.out.println("Car with ID " + carId + " is not available for the given dates.");
                    return;
                }
            }
        }
        System.out.println("No available car with ID " + carId + "found.");
    }

    // Return car by ID
    public void returnCar(String carId){
        for(Car car: carInventory){
            if(car.getId().equals(carId) && !car.isAvailable()){
                car.setAvailable(true);
                car.setRentalPeriod(null, null); //Reset rental period
                availableCarByType.put(car.getCarType(), availableCarByType.get(car.getCarType())+1);
                System.out.println("car returned: " + car.getModel());
                return;
            }
        }
        System.out.println("No car with ID " + carId + " is currently rented.");
    }
}
