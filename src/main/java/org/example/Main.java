package org.example;

import org.example.models.Car;
import org.example.models.Customer;
import org.example.services.CarService;
import org.example.services.Menu;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        CarService carService = new CarService();

        // Add cars to the inventory
        carService.addCar(new Car("CAR001", "Mazda 3", Car.CarType.SEDAN, 55.00)); // Booked by customer1
        carService.addCar(new Car("CAR002", "Tesla S", Car.CarType.SEDAN, 58.00)); // Booked by customer2
        carService.addCar(new Car("CAR003", "Honda CRV", Car.CarType.SEDAN, 59.00));
        carService.addCar(new Car("CAR004", "Audi A4", Car.CarType.SEDAN, 60.00));
        carService.addCar(new Car("CAR005", "BMW 3 Series", Car.CarType.SEDAN, 62.00));
        carService.addCar(new Car("CAR006", "BMW 7 Series", Car.CarType.SEDAN, 65.00));
        carService.addCar(new Car("CAR007", "BYD Seal", Car.CarType.SEDAN, 66.00));
        carService.addCar(new Car("CAR008", "BMW X5", Car.CarType.SUV, 71.00));
        carService.addCar(new Car("CAR009", "Dacia Duster", Car.CarType.SUV, 72.00));
        carService.addCar(new Car("CAR010", "Audi Q7", Car.CarType.SUV, 73.00));
        carService.addCar(new Car("CAR011", "Mokka", Car.CarType.SUV, 74.00));
        carService.addCar(new Car("CAR012", "Range Rover", Car.CarType.SUV, 75.00));
        carService.addCar(new Car("CAR013", "Kia EV9", Car.CarType.SUV, 76.00));
        carService.addCar(new Car("CAR014", "Renault Captur", Car.CarType.SUV, 77.00));
        carService.addCar(new Car("CAR015", "Ford Aerostar", Car.CarType.VAN, 80.00));
        carService.addCar(new Car("CAR016", "Ford Windstar", Car.CarType.VAN, 81.00));
        carService.addCar(new Car("CAR017", "Proton Exora", Car.CarType.VAN, 82.00));
        carService.addCar(new Car("CAR018", "Peugeot Partner", Car.CarType.VAN, 83.00));
        carService.addCar(new Car("CAR019", "Peugeot Boxer", Car.CarType.VAN, 84.00));
        carService.addCar(new Car("CAR020", "Ford Transit", Car.CarType.VAN, 85.00));

        // Add some customers
        Customer customer1 = new Customer("1", "John");
        Customer customer2 = new Customer("2", "Kate");

        // Add some reservations
        carService.rentCar("CAR001", customer1, LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 19));
        carService.rentCar("CAR002", customer2, LocalDate.of(2024, 10, 11), LocalDate.of(2024, 10, 17));

        Menu menu = new Menu(carService);
        menu.showMenu();

    }
}