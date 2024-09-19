package services;

import org.example.models.Car;
import org.example.models.Customer;
import org.example.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {

    private CarService carService;

    @BeforeEach
    public void setUp() {
        carService = new CarService();
        carService.addCar(new Car("CAR001", "Mazda 3", Car.CarType.SEDAN, 55.00));
        carService.addCar(new Car("CAR002", "Tesla S", Car.CarType.SEDAN, 58.00));
    }

    @Test
    public void testAddCar() {
        Car car = new Car("CAR003", "Audi A4", Car.CarType.SEDAN, 60.00);
        carService.addCar(car);
        List<Car> availableCars = carService.getAvailableCarsForDates(null, null, null, null);
        assertTrue(availableCars.contains(car));
    }

    @Test
    public void testRentCar() {
        Customer customer = new Customer("1", "John");
        carService.rentCar("CAR001", customer, LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 19));
        Car rentedCar = carService.getAvailableCarsForDates(null, null, LocalDate.of(2024, 10, 15), LocalDate.of(2024, 10, 19)).get(0);
        assertEquals("CAR001", rentedCar.getId());
        assertFalse(rentedCar.isAvailable());
    }

    @Test
    public void testReturnCar() {
        Customer customer = new Customer("2", "Kate");
        carService.rentCar("CAR002", customer, LocalDate.of(2024, 10, 11), LocalDate.of(2024, 10, 17));
        carService.returnCar("CAR002");
        Car returnedCar = carService.getAvailableCarsForDates(null, null, null, null).stream()
                .filter(car -> car.getId().equals("CAR002"))
                .findFirst()
                .orElse(null);
        assertNotNull(returnedCar);
        assertTrue(returnedCar.isAvailable());
    }
}