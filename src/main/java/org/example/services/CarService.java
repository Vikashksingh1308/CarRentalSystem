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
    // List to store all cars in the inventory
    private List<Car> carInventory;

    // Map to track available cars by their type (e.g., SUV, Sedan) and their count
    private Map<Car.CarType, Integer> availableCarByType;

    // List to keep track of all reservations
    private List<Reservation> reservations;

    // List to store customer data
    private List<Customer> customerList = new ArrayList<>();

    // Add a customer to the customer list
    public void addCustomer(Customer customer) {
        customerList.add(customer);
    }

    // Get the total number of customers
    public int getNumberOfCustomers() {
        return customerList.size();
    }

    // Constructor initializes car inventory, available car types, and reservations
    public CarService() {
        this.carInventory = new ArrayList<>();
        this.availableCarByType = new HashMap<Car.CarType, Integer>();
        this.reservations = new ArrayList<>();
    }

    // Method to add a car to the inventory and update availability count by car type
    public void addCar(Car car){
        carInventory.add(car); // Add car to inventory
        // Increment the available count of the car type
        availableCarByType.put(car.getCarType(), availableCarByType.getOrDefault(car.getCarType(), 0) + 1);
        System.out.println("Car added: " + car.getModel());
    }

    // Show all available cars based on model, type, and date range (start and end dates)
    public void showAvailableCars(String model, String carType, LocalDate startDate, LocalDate endDate) {
        // Get cars available for the specified date range
        List<Car> availableCars = getAvailableCarsForDates(model, carType, startDate, endDate);
        if (!availableCars.isEmpty()) {
            System.out.println("Available cars:");
            // Print available cars
            for (Car car : availableCars) {
                System.out.println(car.toString());
            }
        } else {
            System.out.println("No cars available during the selected period");
        }
    }

    // Method to get available cars based on model, car type, and date range
    public List<Car> getAvailableCarsForDates(String model, String carType, LocalDate startDate, LocalDate endDate) {
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

            // Check if the car is available and if it's available for the given date range
            if (car.isAvailable() && isCarAvailableForDates(car, startDate, endDate)) {
                availableCarsForDates.add(car);
            }
        }
        return availableCarsForDates;
    }

    // Helper method to check if a car is available for the given date range
    private boolean isCarAvailableForDates(Car car, LocalDate startDate, LocalDate endDate) {
        // If no dates are provided, assume the car is available
        if (startDate == null || endDate == null) {
            return true; // Treat as available if no date range is specified
        }

        // Check for conflicts with existing reservations
        for (Reservation reservation : reservations) {
            // If the reservation includes the current car, check for date overlap
            if (reservation.getCar().getId().equals(car.getId())) {
                // If the new reservation dates overlap with an existing reservation, return false
                if (!(endDate.isBefore(reservation.getStartDate()) || startDate.isAfter(reservation.getEndDate()))) {
                    return false;
                }
            }
        }
        return true; // No date overlap, the car is available
    }

    // Rent a car for a specific customer and record a reservation
    public void rentCar(String carId, Customer customer, LocalDate startDate, LocalDate endDate) {
        for (Car car : carInventory) {
            // Check if the car matches the requested ID and is available
            if (car.getId().equals(carId) && car.isAvailable()) {
                // Check if the car is available for the requested dates
                if (isCarAvailableForDates(car, startDate, endDate)) {
                    // Generate a simple reservation ID
                    String reservationId = "R" + (reservations.size() + 1);

                    // Create a new reservation record
                    Reservation reservation = new Reservation(reservationId, customer, car, startDate, endDate);
                    reservations.add(reservation); // Add reservation to the list

                    // Mark the car as unavailable and set its rental period
                    car.setAvailable(false);
                    car.setRentalPeriod(startDate.atStartOfDay(), endDate.atStartOfDay());

                    // Output the details of the reservation
                    System.out.println("Car rented: " + car.getModel() + " for " + ChronoUnit.DAYS.between(startDate, endDate) + " days.");
                    System.out.println("Reservation created: " + reservation);
                    return;
                } else {
                    System.out.println("Car with ID " + carId + " is not available for the given dates.");
                    return;
                }
            }
        }
        System.out.println("No available car with ID " + carId + " found.");
    }

    // Return a rented car by its ID
    public void returnCar(String carId) {
        for (Car car : carInventory) {
            // Check if the car is currently rented (i.e., not available)
            if (car.getId().equals(carId) && !car.isAvailable()) {
                car.setAvailable(true); // Mark the car as available again
                car.setRentalPeriod(null, null); // Reset the rental period

                // Update the availability count by car type
                availableCarByType.put(car.getCarType(), availableCarByType.get(car.getCarType()) + 1);

                System.out.println("Car returned: " + car.getModel());
                return;
            }
        }
        System.out.println("No car with ID " + carId + " is currently rented.");
    }
}
