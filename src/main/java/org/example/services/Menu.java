package org.example.services;

import org.example.models.Customer;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    private final CarService carService; // Reference to the CarService to handle car-related operations
    private final Scanner scanner; // Scanner to take user input
    private LocalDate startDate; // Start date for reservation
    private LocalDate endDate; // End date for reservation

    // Constructor to initialize CarService and Scanner
    public Menu(CarService carService) {
        this.carService = carService;
        this.scanner = new Scanner(System.in);
    }

    // Method to display the main menu and handle user input
    public void showMenu() {
        while (true) { // Infinite loop to keep showing the menu until the user chooses to exit
            System.out.println("\n--- Car Rental Menu ---");
            System.out.println("1. Show available cars");
            System.out.println("2. Reserve a car");
            System.out.println("3. Return a car");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt(); // Capture user choice
            scanner.nextLine(); // Consume the newline character left by nextInt()

            // Handle user choice based on menu option
            switch (choice) {
                case 1:
                    showAvailableCars(); // Option to show available cars
                    break;
                case 2:
                    inputDates(); // Prompt user to input reservation dates
                    reserveCar(); // Reserve a car based on the selected dates
                    break;
                case 3:
                    String carId = inputCarIdToReturn(); // Input car ID to return
                    carService.returnCar(carId); // Call CarService to handle the return process
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return; // Exit the program by breaking the loop
                default:
                    System.out.println("Invalid choice, please try again."); // Handle invalid choices
            }
        }
    }

    // Method to take input for reservation dates (start and end)
    private void inputDates() {
        startDate = inputDate("Enter the start date (YYYY-MM-DD): "); // Input start date
        endDate = inputDate("Enter the end date (YYYY-MM-DD): "); // Input end date
    }

    // Method to show available cars based on car type (optional input)
    private void showAvailableCars() {
        System.out.print("Enter car type (SEDAN, SUV, VAN, or press Enter to skip): ");
        String carType = scanner.nextLine(); // Input car type or leave blank to skip

        // Call CarService to show available cars. No date filter is applied here.
        carService.showAvailableCars(null, carType.isEmpty() ? null : carType, null, null);
    }

    // Method to reserve a car by assigning a new customer ID and taking customer details
    private void reserveCar() {
        // Step 1: Input customer name
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        // Dynamically generate a customer ID based on the current number of customers
        int customerCount = carService.getNumberOfCustomers();
        String customerId = "CUST" + String.format("%02d", customerCount + 1);
        System.out.println("Assigned Customer ID: " + customerId);

        // Create a new customer object with the generated ID and entered name
        Customer customer = new Customer(customerId, customerName);

        // Add the new customer to the customer list
        carService.addCustomer(customer);

        // Step 2: Input car type (optional)
        System.out.print("Enter car type (SEDAN, SUV, VAN, or press Enter to skip): ");
        String carType = scanner.nextLine();

        // Step 3: Show available cars for the input date range and car type (if provided)
        carService.showAvailableCars(null, carType.isEmpty() ? null : carType, startDate, endDate);

        // Step 4: Input the car ID to reserve
        System.out.print("Enter the car ID you want to reserve: ");
        String carId = scanner.nextLine();

        // Step 5: Call CarService to rent the car for the customer for the selected date range
        carService.rentCar(carId, customer, startDate, endDate);
    }

    // Helper method to input dates for reservations
    private LocalDate inputDate(String prompt) {
        System.out.print(prompt); // Show the prompt for the user
        String dateStr = scanner.nextLine(); // Capture the date as a string
        return LocalDate.parse(dateStr); // Convert the string to LocalDate
    }

    // Method to input car ID when returning a car
    private String inputCarIdToReturn() {
        System.out.print("Enter the car ID to return: ");
        return scanner.nextLine(); // Return the input car ID as a string
    }
}