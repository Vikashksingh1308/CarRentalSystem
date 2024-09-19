package services;

import org.example.models.Car;
import org.example.models.Customer;
import org.example.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    private CarService carService;
    private Car car1;
    private Car car2;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        carService = new CarService();

        // Initialize some sample data
        car1 = new Car("C1", "Model S", Car.CarType.SEDAN, 100);
        car2 = new Car("C2", "Model X", Car.CarType.SUV, 150);

        customer = new Customer("Cust1", "John Doe");

        // Add cars to inventory
        carService.addCar(car1);
        carService.addCar(car2);
    }

    @Test
    public void testAddCar() {
        Car car3 = new Car("C3", "Model 3", Car.CarType.SEDAN, 120);
        carService.addCar(car3);
        List<Car> availableCars = carService.getAvailableCarsForDates(null, null, null, null);
        assertEquals(3, availableCars.size());
    }

    @Test
    public void testShowAvailableCars() {
        // Add some test to verify showAvailableCars
        List<Car> availableCars = carService.getAvailableCarsForDates("Model S", "SEDAN", LocalDate.now(), LocalDate.now().plusDays(5));
        assertFalse(availableCars.isEmpty());
        assertEquals(1, availableCars.size());
        assertEquals("Model S", availableCars.get(0).getModel());
    }

    @Test
    public void testRentCar() {
        // Rent a car
        carService.rentCar(car1.getId(), customer, LocalDate.now(), LocalDate.now().plusDays(5));

        // Verify the car is no longer available
        assertFalse(car1.isAvailable());
    }

    @Test
    public void testReturnCar() {
        // Rent and then return the car
        carService.rentCar(car1.getId(), customer, LocalDate.now(), LocalDate.now().plusDays(5));
        carService.returnCar(car1.getId());

        // Verify the car is now available again
        assertTrue(car1.isAvailable());
    }

    @Test
    public void testGetAvailableCarsForDates() {
        // Check available cars for specific dates
        List<Car> availableCars = carService.getAvailableCarsForDates(null, null, LocalDate.now(), LocalDate.now().plusDays(5));
        assertEquals(2, availableCars.size());

        // Rent a car and check available cars again
        carService.rentCar(car1.getId(), customer, LocalDate.now(), LocalDate.now().plusDays(5));
        availableCars = carService.getAvailableCarsForDates(null, null, LocalDate.now(), LocalDate.now().plusDays(5));
        assertEquals(1, availableCars.size()); // Should only have one car available
    }

    @Test
    public void testAddCustomer() {
        // Add a customer and verify the count
        carService.addCustomer(customer);
        assertEquals(1, carService.getNumberOfCustomers());
    }

    @Test
    public void testGetNumberOfCustomers() {
        // Add multiple customers and verify the count
        carService.addCustomer(new Customer("Cust2", "Jane Doe"));
        carService.addCustomer(customer);
        assertEquals(2, carService.getNumberOfCustomers());
    }
}
