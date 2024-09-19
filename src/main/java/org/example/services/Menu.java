package org.example.services;

import org.example.models.Customer;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    private final CarService carService;
    private final Scanner scanner;
    private LocalDate startDate;
    private LocalDate endDate;

    public Menu(CarService carService) {
        this.carService = carService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Car Rental Menu ---");
            System.out.println("1. Show available cars");
            System.out.println("2. Reserve a car");
            System.out.println("3. Return a car");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    showAvailableCars();
                    break;
                case 2:
                    inputDates();
                    reserveCar();
                    break;
                case 3:
                    String carId = inputCarIdToReturn();
                    carService.returnCar(carId);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return; // Exit the loop and program
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void inputDates() {
        startDate = inputDate("Enter the start date (YYYY-MM-DD): ");
        endDate = inputDate("Enter the end date (YYYY-MM-DD): ");
    }

    private void showAvailableCars() {
        System.out.print("Enter car type (SEDAN, SUV, VAN, or press Enter to skip): ");
        String carType = scanner.nextLine();

        // Showing available cars without date filtering
        carService.showAvailableCars(null, carType.isEmpty() ? null : carType, null, null);
    }

    private void reserveCar() {
        // Dynamically generate customer ID based on the number of customers
        int customerCount = carService.getNumberOfCustomers();
        String customerId = "CUST" + (customerCount + 1);
        System.out.println("Assigned Customer ID: " + customerId);

        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        Customer customer = new Customer(customerId, customerName);

        System.out.print("Enter the car ID you want to reserve: ");
        String carId = scanner.nextLine();

        carService.rentCar(carId, customer, startDate, endDate);
    }

    private LocalDate inputDate(String prompt) {
        System.out.print(prompt);
        String dateStr = scanner.nextLine();
        return LocalDate.parse(dateStr); // Parse the input date
    }

    private String inputCarIdToReturn() {
        System.out.print("Enter the car ID to return: ");
        return scanner.nextLine();
    }
}
